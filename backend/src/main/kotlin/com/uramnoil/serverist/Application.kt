package com.uramnoil.serverist

import com.auth0.jwt.algorithms.Algorithm
import com.uramnoil.serverist.application.service.usecases.user.queries.FindUserByNameDto
import com.uramnoil.serverist.application.service.usecases.user.queries.FindUserByNameOutputPort
import com.uramnoil.serverist.application.service.usecases.user.queries.FindUserByNameOutputPortDto
import com.uramnoil.serverist.application.service.usecases.user.queries.FindUserByNameQuery
import com.uramnoil.serverist.infrastracture.Servers
import com.uramnoil.serverist.infrastracture.Users
import com.uramnoil.serverist.infrastracture.buildDi
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.sessions.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.kodein.di.instance


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@ExperimentalCoroutinesApi
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val secret = System.getenv("SERVERIST_JWT_SECRET")
    val issuer = environment.config.property("jwt.issuer").getString()
    val audiences = environment.config.property("jwt.audiences").getList()
    val algorithm = Algorithm.HMAC256(secret)

    val database = Database.connect(
        "jdbc:mysql://localhost:3306/develop",
        driver = "com.mysql.cj.jdbc.Driver",
        user = "develop",
        password = "develop"
    )
    val context = Dispatchers.Default

    val di = buildDi(database, context)

    if (testing) {
        transaction(database) {
            SchemaUtils.drop(Users, Servers)
        }
    }

    transaction {
        SchemaUtils.create(Users, Servers)
    }

    data class AuthSession(val id: String)

    install(Sessions) {
        cookie<AuthSession>("SESSION", SessionStorageMemory()) {
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
            call.receive<IdEmailPassword>().let {
                val channel = Channel<FindUserByNameOutputPortDto?>()

                val query: FindUserByNameQuery by di.instance(arg = FindUserByNameOutputPort { dto ->
                    channel.offer(dto)
                    channel.close()
                })

                query.excecute(FindUserByNameDto(it.idOrEmail))

                channel.receive()?.let {
                }
            }
        }

        post("signup") {
            data class IdEmailPassword(val id: String, val email: String, val password: String)
            call.receive<IdEmailPassword>().let {

            }
        }
    }
}