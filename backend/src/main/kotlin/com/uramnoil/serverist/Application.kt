package com.uramnoil.serverist

import com.apurebase.kgraphql.GraphQL
import com.uramnoil.serverist.application.kernel.User
import com.uramnoil.serverist.application.unauthenticateduser.service.RequestToCreateUserService
import com.uramnoil.serverist.application.unauthenticateduser.service.RequestToCreateUserServiceDto
import com.uramnoil.serverist.application.user.queries.ValidateLoginService
import com.uramnoil.serverist.application.user.queries.ValidateLoginServiceDto
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
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.kodein.di.DI
import org.kodein.di.instance
import java.util.*

data class AuthSession(val id: UUID) : Principal

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val di = buildApplicationDi(buildDomainDi(Dispatchers.Default))

    createConnection()

    install(Sessions) {
        cookie<AuthSession>("SESSION", SessionStorageMemory()) {
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
        Database.connect(
            url = property("database.url").getString(),
            driver = property("database.driver").getString(),
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

            val service: ValidateLoginService by di.instance()

            val user: User =
                service.execute(ValidateLoginServiceDto(idEmailPassword.idOrEmail, idEmailPassword.password))
                    ?: return@post call.respond(HttpStatusCode.Unauthorized)

            call.sessions.set(AuthSession(user.id))

            call.respond(HttpStatusCode.OK)
        }
    }

    post("signup") {
        data class IdEmailPassword(val accountId: String, val email: String, val password: String)
        call.receive<IdEmailPassword>().let {
            val service: RequestToCreateUserService by di.instance()
            service.execute(RequestToCreateUserServiceDto(it.accountId, it.email, it.password))
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
        stringScalar<UUID> {
            deserialize = { UUID.fromString(it) }
            serialize = UUID::toString
        }

        type<PageRequest>()

        serverSchema(di)
        userSchema(di)
    }
}
