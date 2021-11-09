package com.uramnoil.serverist.backend

import com.icegreen.greenmail.util.GreenMail
import com.icegreen.greenmail.util.GreenMailUtil
import com.icegreen.greenmail.util.ServerSetupTest
import com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.services.SpringBootSendEmailToAuthenticateService
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.test.*

internal class SpringBootSendEmailToAuthenticateServiceTest {
    private val greenMail = GreenMail(ServerSetupTest.SMTP)

    @BeforeTest
    fun before() {
        greenMail.start()
    }

    @AfterTest
    fun after() {
        greenMail.stop()
    }

    @Test
    fun `送信テスト`() {
        val service = SpringBootSendEmailToAuthenticateService(
            host = "localhost",
            port = 3025,
            username = "",
            password = "",
            from = "test@serverist.com",
            activateUrl = "http://localhost/auth",
        )

        val code = UUID.randomUUID()

        runBlocking {
            service.execute("hoge.com", code)
        }

        assertTrue(greenMail.waitForIncomingEmail(3000, 1))

        val messages = greenMail.receivedMessages
        assertEquals(messages.first().let { GreenMailUtil.getBody(it) }, "http://localhost/auth?code=$code")
    }
}