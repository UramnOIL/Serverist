package com.uramnoil.serverist

import com.apurebase.kgraphql.GraphQL
import com.uramnoil.serverist.application.unauthenticateduser.queries.FindUnauthenticatedUserByAccountIdQuery
import com.uramnoil.serverist.application.unauthenticateduser.queries.FindUnauthenticatedUserByAccountIdQueryOutputPort
import com.uramnoil.serverist.application.user.queries.FindUserByNameDto
import com.uramnoil.serverist.application.user.queries.FindUserByNameQuery
import com.uramnoil.serverist.infrastracture.server.Servers
import com.uramnoil.serverist.infrastracture.user.Users
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.sessions.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.kodein.di.instance


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



    install(GraphQL) {
        playground = true

        schema {
            query("findUserByAccountId") {
                resolver { ->
                    val query: FindUnauthenticatedUserByAccountIdQuery by di.instance()
                }
            }
        }
    }

    data class UserPasswordPrincipal(val user: String, val password: String) : Principal

    routing {
        post("login") {
            data class IdEmailPassword(val idOrEmail: String, val password: String)
            call.receive<IdEmailPassword>().let { idEmailPassword ->
                val output = FindUnauthenticatedUserByAccountIdQueryOutputPort { dto ->
                    dto.let {

                    } ?: let {
                        TODO("Emailで検索")
                    }
                }

                val query: FindUserByNameQuery by di.instance(arg = output)

                query.execute(FindUserByNameDto(idEmailPassword.idOrEmail))
            }
        }

        post("signup") {
            data class IdEmailPassword(val id: String, val email: String, val password: String)
            call.receive<IdEmailPassword>().let {

            }
        }
    }
}