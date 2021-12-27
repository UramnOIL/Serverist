package com.uramnoil.serverist.backend.integrate

import com.icegreen.greenmail.util.GreenMail
import com.icegreen.greenmail.util.GreenMailUtil
import com.icegreen.greenmail.util.ServerSetupTest
import com.uramnoil.serverist.auth.infrastructure.AuthenticatedUsers
import com.uramnoil.serverist.auth.infrastructure.UnauthenticatedUsers
import com.uramnoil.serverist.auth.infrastructure.domain.kernel.service.HashPasswordServiceImpl
import com.uramnoil.serverist.domain.auth.kernel.model.HashedPassword
import com.uramnoil.serverist.domain.auth.kernel.model.Password
import com.uramnoil.serverist.mainModule
import com.uramnoil.serverist.serverist.infrastructure.Servers
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.ktor.application.Application
import io.ktor.config.MapApplicationConfig
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
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
import com.uramnoil.serverist.serverist.infrastructure.Users as ServeristUsers

class AuthTest : FunSpec({
    val greenMail = GreenMail(ServerSetupTest.SMTP)

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
        greenMail.start()

        transaction {
            SchemaUtils.create(
                ServeristUsers,
                Servers,
                UnauthenticatedUsers,
                AuthenticatedUsers,
            )
        }

        transaction {
            AuthenticatedUsers.insert {
                it[id] = UUID.randomUUID()
                it[email] = "uramnoil@example.com"
                it[hashedPassword] = HashPasswordServiceImpl().hash(Password("abcd1234")).value
            }
            commit()
        }
    }

    afterTest {
        greenMail.stop()

        transaction {
            SchemaUtils.drop(
                ServeristUsers,
                Servers,
                AuthenticatedUsers,
                UnauthenticatedUsers
            )
        }
    }

    test("/signup test") {
        withTestApplication(
            moduleFunction = {
                mailConfig()
                mainModule()
            }
        ) {
            val email = "hoge@example.com"
            val password = "abcd1234"
            val hashPasswordService = HashPasswordServiceImpl()

            with(
                handleRequest(HttpMethod.Post, "/signup") {
                    addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    setBody(Json.encodeToString(mapOf("email" to email, "password" to password)))
                }
            ) {
                response.status() shouldBe HttpStatusCode.OK
                greenMail.waitForIncomingEmail(3000, 1).shouldBeTrue()
                val mail = greenMail.receivedMessages.firstOrNull()
                mail.shouldNotBeNull()

                val row = transaction {
                    UnauthenticatedUsers.select { UnauthenticatedUsers.email eq email }.firstOrNull()
                }
                row shouldNotBe null

                // Email
                row!![UnauthenticatedUsers.email] shouldBe email
                // Password
                hashPasswordService.check(
                    Password(password),
                    HashedPassword(row[UnauthenticatedUsers.hashedPassword])
                ) shouldBe true
                // EmailBody
                "http://localhost:8080/activate?code=${row[UnauthenticatedUsers.activateCode]}" shouldBe GreenMailUtil.getBody(
                    mail
                )
            }
        }
    }

    test("/activate test") {
        withTestApplication(
            moduleFunction = {
                mailConfig()
                mainModule()
            }
        ) {
            val email = "hoge@example.com"
            val password = "abcd1234"
            handleRequest(HttpMethod.Post, "/signup") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Json.encodeToString(mapOf("email" to email, "password" to password)))
            }

            val unauthenticatedRow = transaction {
                UnauthenticatedUsers.select { UnauthenticatedUsers.email eq email }.firstOrNull()
            }
            unauthenticatedRow.shouldNotBeNull()
            val activationCode = unauthenticatedRow[UnauthenticatedUsers.activateCode]

            with(
                handleRequest(HttpMethod.Post, "/activate") {
                    addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    setBody(Json.encodeToString(mapOf("email" to email, "activationCode" to activationCode)))
                }
            ) {
                response.status() shouldBe HttpStatusCode.OK
            }

            val authenticatedRow = transaction {
                AuthenticatedUsers.select { AuthenticatedUsers.email eq email }.firstOrNull()
            }
            authenticatedRow.shouldNotBeNull()

            val id = authenticatedRow[AuthenticatedUsers.id]
            val serveristRow = transaction {
                ServeristUsers.select { ServeristUsers.id eq id }.firstOrNull()
            }
            serveristRow.shouldNotBeNull()
        }
    }

    test("/signin test") {
        withTestApplication(
            moduleFunction = {
                mailConfig()
                mainModule()
            }
        ) {
            val email = "hoge@example.com"
            val password = "abcd1234"
            val hashedPassword = HashPasswordServiceImpl().hash(Password(password)).value
            val uuid = UUID.randomUUID()
            transaction {
                AuthenticatedUsers.insert {
                    it[AuthenticatedUsers.id] = uuid
                    it[AuthenticatedUsers.email] = email
                    it[AuthenticatedUsers.hashedPassword] = hashedPassword
                }
            }
            with(
                handleRequest(HttpMethod.Post, "/signin") {
                    addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    setBody(Json.encodeToString(mapOf("email" to email, "password" to password)))
                }
            ) {
                response.status() shouldBe HttpStatusCode.OK
                response.headers["Auth"] shouldNotBe null
            }
        }
    }

    test("/withdrawal test") {
        withTestApplication(
            moduleFunction = {
                mailConfig()
                mainModule()
            }
        ) {
            val email = "hoge@example.com"
            val password = "abcd1234"
            val hashedPassword = HashPasswordServiceImpl().hash(Password(password)).value
            val uuid = UUID.randomUUID()
            transaction {
                AuthenticatedUsers.insert {
                    it[AuthenticatedUsers.id] = uuid
                    it[AuthenticatedUsers.email] = email
                    it[AuthenticatedUsers.hashedPassword] = hashedPassword
                }
                ServeristUsers.insert {
                    it[id] = uuid
                    it[accountId] = "hoge"
                    it[name] = "hoge"
                    it[description] = "hoge"
                }
            }
            var sessionId: String?
            with(
                handleRequest(HttpMethod.Post, "/signin") {
                    addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    setBody(Json.encodeToString(mapOf("email" to email, "password" to password)))
                }
            ) {
                response.status() shouldBe HttpStatusCode.OK
                sessionId = response.headers["Auth"]
            }
            sessionId ?: error("サインイン失敗")
            with(
                handleRequest(HttpMethod.Post, "/withdrawal") {
                    this.addHeader("Auth", sessionId!!)
                }
            ) {
                response.status() shouldBe HttpStatusCode.OK
            }

            val row = transaction {
                AuthenticatedUsers.select { AuthenticatedUsers.email eq email }.firstOrNull()
            }

            row.shouldBeNull()
        }
    }

    test("/signout test") {
        withTestApplication(
            moduleFunction = {
                mailConfig()
                mainModule()
            }
        ) {
            val email = "hoge@example.com"
            val password = "abcd1234"
            val hashedPassword = HashPasswordServiceImpl().hash(Password(password)).value
            val uuid = UUID.randomUUID()
            transaction {
                AuthenticatedUsers.insert {
                    it[AuthenticatedUsers.id] = uuid
                    it[AuthenticatedUsers.email] = email
                    it[AuthenticatedUsers.hashedPassword] = hashedPassword
                }
                ServeristUsers.insert {
                    it[id] = uuid
                    it[accountId] = "hoge"
                    it[name] = "hoge"
                    it[description] = "hoge"
                }
            }
            var sessionId: String?
            with(
                handleRequest(HttpMethod.Post, "/signin") {
                    addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    setBody(Json.encodeToString(mapOf("email" to email, "password" to password)))
                }
            ) {
                response.status() shouldBe HttpStatusCode.OK
                sessionId = response.headers["Auth"]
            }
            sessionId ?: error("サインイン失敗")
            with(
                handleRequest(HttpMethod.Post, "/signout") {
                    this.addHeader("Auth", sessionId!!)
                }
            ) {
                response.status() shouldBe HttpStatusCode.OK
            }
        }
    }
})
