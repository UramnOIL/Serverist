package com.uramnoil.serverist.backend.integrate

import com.uramnoil.serverist.auth.infrastructure.AuthenticatedUsers
import com.uramnoil.serverist.auth.infrastructure.domain.kernel.service.HashPasswordServiceImpl
import com.uramnoil.serverist.domain.auth.kernel.model.Password
import com.uramnoil.serverist.mainModule
import com.uramnoil.serverist.serverist.infrastructure.Servers
import com.uramnoil.serverist.serverist.infrastructure.toJavaLocalDataTime
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.ktor.application.*
import io.ktor.config.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.datetime.Clock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*
import com.uramnoil.serverist.serverist.infrastructure.Users as ServeristUsers

class ServerTest : FunSpec({
    val uuid = UUID.randomUUID()
    val anEmail = "uramnoil@example.com"
    val aPassword = "hoge1234"

    val aCreatedAt = Clock.System.now()

    fun Application.mailConfig() {
        (environment.config as MapApplicationConfig).apply {
            put("mail.host", "localhost")
            put("mail.port", "3025")
            put("mail.from", "test@serverist.com")
            put("mail.user", "")
            put("mail.password", "")
            put("mail.activate_url", "http://localhost:8080/activate")
        }
    }

    beforeSpec {
        Database.connect(
            "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            driver = "org.h2.Driver",
            user = "sa",
            password = ""
        )
    }

    beforeTest {
        transaction {
            SchemaUtils.create(
                AuthenticatedUsers,
                ServeristUsers,
                Servers,
            )
        }

        transaction {
            val service = HashPasswordServiceImpl()
            AuthenticatedUsers.insert {
                it[id] = uuid
                it[email] = anEmail
                it[hashedPassword] = service.hash(Password(aPassword)).value
            }
            ServeristUsers.insert {
                it[id] = uuid
                it[accountId] = "uramnoil"
                it[name] = "hoge"
                it[description] = "hoge"
            }
            Servers.insert {
                it[id] = uuid
                it[ownerId] = uuid
                it[name] = "TestServer1"
                it[description] = "description"
                it[host] = "example.com"
                it[port] = 19132u
                it[createdAt] = aCreatedAt.toJavaLocalDataTime()
            }

            commit()
        }
    }

    afterTest {
        transaction {
            SchemaUtils.drop(
                AuthenticatedUsers,
                com.uramnoil.serverist.serverist.infrastructure.Users,
                Servers,
            )
        }
    }

    test("findServer") {
        withTestApplication(
            moduleFunction = {
                mailConfig()
                mainModule()
            }
        ) {
            with(handleRequest(HttpMethod.Post, "/graphql") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Json.encodeToString(mapOf("query" to "query { findServer(id: \"$uuid\") { id, name, ownerId, description, host, port, createdAt } }"))
                )
            }) {
                response.status() shouldBe HttpStatusCode.OK
                response.content!! shouldBe """{"data":{"findServer":{"id":"$uuid","name":"TestServer1","ownerId":"$uuid","description":"description","host":"example.com","port":19132,"createdAt":${aCreatedAt.toEpochMilliseconds()}}}}"""
            }
        }
    }

    test("findServersByOwner") {
        withTestApplication(
            moduleFunction = {
                mailConfig()
                mainModule()
            }
        ) {
            with(handleRequest(HttpMethod.Post, "/graphql") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Json.encodeToString(mapOf("query" to "query { findServersByOwner( ownerId: \"$uuid\") { ownerId } }"))
                )
            }) {
                response.status() shouldBe HttpStatusCode.OK
                response.content!! shouldBe """{"data":{"findServersByOwner":[{"ownerId":"$uuid"}]}}"""
            }
        }
    }

    test("createServer") {
        withTestApplication(
            moduleFunction = {
                mailConfig()
                mainModule()
            }
        ) {
            cookiesSession {
                with(handleRequest(HttpMethod.Post, "/login") {
                    addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    setBody(
                        Json.encodeToString(mapOf("email" to anEmail, "password" to aPassword))
                    )
                }) {
                    response.status() shouldBe HttpStatusCode.OK
                }
                val createName = "TestServer"
                val createDescription = "piyo"
                val createHost = "example.com"
                val createPort: UShort = 19132u
                with(handleRequest(HttpMethod.Post, "/graphql") {
                    addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    setBody(
                        Json.encodeToString(mapOf("query" to """mutation { createServer(name: "$createName" host: "$createHost" port: $createPort description: "$createDescription") }"""))
                    )
                }) {
                    response.status() shouldBe HttpStatusCode.OK
                }

                val row = transaction {
                    Servers.select {
                        (Servers.name eq createName) and
                                (Servers.description eq createDescription) and
                                (Servers.host eq createHost) and
                                (Servers.port eq createPort)
                    }.firstOrNull()
                }
                row.shouldNotBeNull()
            }
        }
    }

    test("updateServer") {
        withTestApplication(
            moduleFunction = {
                mailConfig()
                mainModule()
            }
        ) {
            cookiesSession {
                with(handleRequest(HttpMethod.Post, "/login") {
                    addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    setBody(
                        Json.encodeToString(mapOf("email" to anEmail, "password" to aPassword))
                    )
                }) {
                    response.status() shouldBe HttpStatusCode.OK
                }
                val updatedName = "TestServer2"
                val updatedDescription = "piyo"
                val updatedHost = "hoge.example.com"
                val updatedPort = 19133u
                with(handleRequest(HttpMethod.Post, "/graphql") {
                    addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    setBody(
                        Json.encodeToString(mapOf("query" to """mutation { updateServer(id: "$uuid" name: "$updatedName" host: "$updatedHost" port: $updatedPort description: "$updatedDescription") }"""))
                    )
                }) {
                    response.status() shouldBe HttpStatusCode.OK
                    response.content!! shouldBe """{"data":{"updateServer":true}}"""
                }
            }
        }
    }
})