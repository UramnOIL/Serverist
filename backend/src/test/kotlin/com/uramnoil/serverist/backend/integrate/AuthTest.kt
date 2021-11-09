package com.uramnoil.serverist.backend.integrate

import com.icegreen.greenmail.util.GreenMail
import com.icegreen.greenmail.util.ServerSetupTest
import com.uramnoil.serverist.mainModule
import com.uramnoil.serverist.serverist.infrastructure.Servers
import com.uramnoil.serverist.serverist.infrastructure.Users
import io.ktor.application.*
import io.ktor.config.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.test.*

class AuthTest {
    private val greenMail = GreenMail(ServerSetupTest.SMTP)

    // メール設定
    private fun Application.mailConfig() {
        (environment.config as MapApplicationConfig).apply {
            put("mail.host", "localhost")
            put("mail.port", "3025")
            put("mail.from", "test@serverist.com")
            put("mail.user", "")
            put("mail.password", "")
            put("mail.activate_url", "http://localhost:8080/activate")
        }
    }

    @BeforeTest
    fun before() {
        Database.connect(
            "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            driver = "org.h2.Driver",
            user = "sa",
            password = ""
        )

        transaction {
            SchemaUtils.create(
                Users, Servers,
                com.uramnoil.serverist.auth.infrastructure.authenticated.Users,
                com.uramnoil.serverist.auth.infrastructure.unauthenticated.Users
            )
        }
        greenMail.start()
    }

    @AfterTest
    fun after() {
        greenMail.stop()
    }

    @Test
    fun testRegister(): Unit = withTestApplication(
        moduleFunction = {
            mailConfig()
            mainModule(true)
        }
    ) {
        with(handleRequest(HttpMethod.Post, "/signup") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(Json.encodeToString(mapOf("email" to "hoge@example.com", "password" to "hogefuga1234")))
        }) {
            assertEquals(HttpStatusCode.OK, response.status(), response.content)
            assertTrue(greenMail.waitForIncomingEmail(3000, 1))
            val mail = greenMail.receivedMessages.firstOrNull()
            assertNotNull(mail)
        }
    }
}