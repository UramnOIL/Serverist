package com.uramnoil.serverist.backend.integrate

import com.uramnoil.serverist.auth.infrastructure.AuthenticatedUsers
import com.uramnoil.serverist.auth.infrastructure.domain.kernel.service.HashPasswordServiceImpl
import com.uramnoil.serverist.domain.auth.kernel.model.Password
import com.uramnoil.serverist.mainModule
import com.uramnoil.serverist.serverist.infrastructure.Servers
import com.uramnoil.serverist.serverist.infrastructure.Users
import com.uramnoil.serverist.serverist.infrastructure.toApplicationUser
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.ktor.application.Application
import io.ktor.config.MapApplicationConfig
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.cookiesSession
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class UserTest : FunSpec({
    val uuid = UUID.randomUUID()
    val anEmail = "uramnoil@example.com"
    val aPassword = "hoge1234"

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
                Users,
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
            Users.insert {
                it[id] = uuid
                it[this.accountId] = "uramnoil1"
                it[name] = "うらむんオイル"
                it[description] = "fuga"
            }
            Users.insert {
                it[id] = UUID.randomUUID()
                it[this.accountId] = "uramnoil2"
                it[name] = "うらむんオイル2"
                it[description] = "fuga"
            }
            commit()
        }
    }

    afterTest {
        transaction {
            SchemaUtils.drop(
                AuthenticatedUsers,
                Users,
                Servers,
            )
        }
    }

    test("findById") {
        withTestApplication(
            moduleFunction = {
                mailConfig()
                mainModule()
            }
        ) {
            with(handleRequest(HttpMethod.Post, "/graphql") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Json.encodeToString(mapOf("query" to "query { findUserById(id: \"$uuid\") { name } }"))
                )
            }) {
                response.status() shouldBe HttpStatusCode.OK
                val content = response.content
                content shouldNotBe null
                content shouldBe """{"data":{"findUserById":{"name":"うらむんオイル"}}}"""
            }
        }
    }

    test("update") {
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
                val updatedName = "UramnOIL"
                val updatedAccountId = "uramnoil3"
                val updatedDescription = "piyo"
                with(handleRequest(HttpMethod.Post, "/graphql") {
                    addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    setBody(
                        Json.encodeToString(mapOf("query" to "mutation { update(name:\"$updatedName\", accountId: \"$updatedAccountId\", description: \"$updatedDescription\") }"))
                    )
                }) {
                    response.status() shouldBe HttpStatusCode.OK
                    val content = response.content
                    content shouldNotBe null
                    content shouldBe """{"data":{"update":true}}"""
                }
                val row = transaction {
                    Users.select { Users.id eq uuid }.firstOrNull()
                }
                row shouldNotBe null
                row!!
                val user = row.toApplicationUser()
                user.name shouldBe updatedName
                user.accountId shouldBe updatedAccountId
                user.description shouldBe updatedDescription
            }
        }
    }
})