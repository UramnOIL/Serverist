package com.uramnoil.serverist.backend.auth.infrastructure.unauthenticated.services

import com.icegreen.greenmail.util.GreenMail
import com.icegreen.greenmail.util.GreenMailUtil
import com.icegreen.greenmail.util.ServerSetupTest
import com.uramnoil.serverist.auth.infrastructure.application.SpringBootSendEmailService
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

internal class SpringBootSendEmailServiceTest : FunSpec({
    val greenMail = GreenMail(ServerSetupTest.SMTP)

    beforeTest {
        greenMail.start()
    }

    afterTest {
        greenMail.stop()
    }

    context("正常系") {
        test("送信テスト") {
            val service = SpringBootSendEmailService(
                host = "localhost",
                port = 3025,
                username = "",
                password = "",
                from = "test@serverist.com",
                activateUrl = "http://localhost:8080/activate",
            )

            val code = UUID.randomUUID()
            service.sendActivationEmail("hoge.com", code)

            withContext(Dispatchers.Default) {
                greenMail.waitForIncomingEmail(3000, 1).shouldBeTrue()
            }

            val messages = greenMail.receivedMessages
            messages.first().let { GreenMailUtil.getBody(it) } shouldBe "http://localhost:8080/activate?code=$code"
        }
    }
})