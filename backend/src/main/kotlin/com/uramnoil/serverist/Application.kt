package com.uramnoil.serverist

import com.apurebase.kgraphql.GraphQL
import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.application.server.queries.OrderBy
import com.uramnoil.serverist.application.unauthenticateduser.commands.CreateUnauthenticatedUserCommand
import com.uramnoil.serverist.application.unauthenticateduser.commands.DeleteUnauthenticatedUserCommand
import com.uramnoil.serverist.application.unauthenticateduser.queries.FindUnauthenticatedUserByIdQuery
import com.uramnoil.serverist.application.unauthenticateduser.service.SendEmailToAuthenticateService
import com.uramnoil.serverist.application.user.queries.GetUserIfCorrectLoginInfoQuery
import com.uramnoil.serverist.graphql.PageRequest
import com.uramnoil.serverist.graphql.serverSchema
import com.uramnoil.serverist.graphql.userSchema
import com.uramnoil.serverist.infrastracture.server.Servers
import com.uramnoil.serverist.infrastracture.user.Users
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.util.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.kodein.di.DI
import org.kodein.di.instance
import java.io.File

data class AuthSession(val id: Uuid) : Principal

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val di = environment.buildApplicationDi(environment.buildDomainDi(DI {}))

    createConnection()

    install(Sessions) {
        cookie<AuthSession>("SESSION", directorySessionStorage(File(".sessions"), cached = true)) {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 1000
        }
    }

    install(Authentication) {
        session<AuthSession>()
    }

    routingLogin(di)
    buildGraphql(di)
}

fun Application.createConnection() {
    environment.config.apply {
        val host = property("database.host").getString()
        val database = property("database.database").getString()
        val port = property("database.port").getString()

        Database.connect(
            url = "jdbc:mysql://${host}:${port}/${database}?characterEncoding=utf8&useSSL=false",
            driver = com.mysql.cj.jdbc.Driver::class.qualifiedName!!,
            user = property("database.user").getString(),
            password = property("database.password").getString()
        )

        transaction {
            SchemaUtils.create(Users, Servers)
        }
    }
}

fun Application.routingLogin(di: DI) = routing {
    post("login") {
        data class IdEmailPassword(val idOrEmail: String, val password: String)
        call.receive<IdEmailPassword>().let { idEmailPassword ->
            call.sessions.get<AuthSession>()?.let {
                return@post
            }

            val service: GetUserIfCorrectLoginInfoQuery by di.instance()

            val user = service.execute(idEmailPassword.idOrEmail, idEmailPassword.password).getOrElse {
                call.application.environment.log.error(it)
                return@post call.respond(it)
            } ?: return@post call.respond(HttpStatusCode.Unauthorized)

            call.sessions.set(AuthSession(user.id))

            call.respond(HttpStatusCode.OK)
        }
    }

    post("signup") {
        data class IdEmailPassword(val accountId: String, val email: String, val password: String)
        call.receive<IdEmailPassword>().let { idEmailPassword ->
            val command by di.instance<CreateUnauthenticatedUserCommand>()
            val user = command.execute(
                idEmailPassword.accountId,
                idEmailPassword.email,
                idEmailPassword.password
            ).getOrElse {
                call.application.environment.log.error(it)
                return@post call.respond(it)
            }

            val service by di.instance<SendEmailToAuthenticateService>()
            service.execute(user).onFailure {
                call.application.environment.log.error(it)
                return@post call.respond(it)
            }
        }
    }

    post("auth") {
        data class AuthenticateUserId(val id: Uuid)

        val auth = call.receive<AuthenticateUserId>()
        val query by di.instance<FindUnauthenticatedUserByIdQuery>()
        val user = query.execute(auth.id).getOrElse {
            call.application.environment.log.error(it)
            return@post call.respond(it)
        }

        if (user != null) {
            call.respond(HttpStatusCode.OK)
            val command by di.instance<DeleteUnauthenticatedUserCommand>()
            command.execute(user.id)
        } else {
            call.respond(HttpStatusCode.BadRequest, "無効なリクエスト")
        }
    }
}

fun Application.buildGraphql(di: DI) = install(GraphQL) {
    playground = true

    wrap {
        authenticate(optional = true, build = it)
    }

    context { call ->
        call.authentication.principal<AuthSession>()?.let {
            +it
        }
    }

    schema {
        stringScalar<Uuid> {
            deserialize = { Uuid.fromString(it) }
            serialize = Uuid::toString
        }

        type<PageRequest>()
        enum<Sort>()
        enum<OrderBy>()

        serverSchema(di)
        userSchema(di)
    }
}
