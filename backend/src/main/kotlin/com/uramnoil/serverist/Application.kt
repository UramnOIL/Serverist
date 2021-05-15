package com.uramnoil.serverist

import com.apurebase.kgraphql.GraphQL
import com.uramnoil.serverist.application.unauthenticateduser.service.RequestToCreateUserService
import com.uramnoil.serverist.application.unauthenticateduser.service.RequestToCreateUserServiceDto
import com.uramnoil.serverist.application.user.User
import com.uramnoil.serverist.application.user.queries.ValidateLoginService
import com.uramnoil.serverist.application.user.queries.ValidateLoginServiceDto
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.jetbrains.exposed.sql.Database
import org.kodein.di.instance
import java.util.*


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@ExperimentalCoroutinesApi
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val database = Database.connect(
        "jdbc:mysql://localhost:3306/develop",
        driver = "com.mysql.cj.jdbc.Driver",
        user = "develop",
        password = "develop"
    )

    val context = Dispatchers.Default

    val di = buildApplicationDi(buildDomainDi(database, context), database, context)

    data class AuthSession(val id: UUID) : Principal

    install(Sessions) {
        cookie<AuthSession>("SESSION", if (testing) TODO("Redis用Storageを作成") else SessionStorageMemory()) {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 1000
        }
    }

    install(Authentication) {
        session<AuthSession> {
            validate { session ->
                TODO()
            }
        }
    }

    data class UserPasswordPrincipal(val user: String, val password: String) : Principal

    routing {
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

    install(GraphQL) {
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
        }
    }
}
