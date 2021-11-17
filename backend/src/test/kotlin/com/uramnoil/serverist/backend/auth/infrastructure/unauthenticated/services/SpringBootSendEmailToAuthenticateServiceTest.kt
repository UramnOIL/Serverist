package com.uramnoil.serverist.backend.auth.infrastructure.unauthenticated.services

import com.icegreen.greenmail.util.GreenMail
import com.icegreen.greenmail.util.GreenMailUtil
import com.icegreen.greenmail.util.ServerSetupTest
import com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.services.SpringBootSendEmailToAuthenticateServiceInputPort
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.currentCoroutineContext
import java.util.*

internal class SpringBootSendEmailToAuthenticateServiceTest : FunSpec({
    val greenMail = GreenMail(ServerSetupTest.SMTP)

    beforeTest {
        greenMail.start()
    }

    afterTest {
        greenMail.stop()
    }

    context("正常系") {
        test("送信テスト") {
            val coroutineContext = currentCoroutineContext()
            val service = SpringBootSendEmailToAuthenticateServiceInputPort(
                host = "localhost",
                port = 3025,
                username = "",
                password = "",
                from = "test@serverist.com",
                activateUrl = "http://localhost/auth",
                {
                    it.getOrThrow()
                },
                coroutineContext
            )

            val code = UUID.randomUUID()

            service.execute("hoge.com", code)

            greenMail.waitForIncomingEmail(3000, 1).shouldBeTrue()

            val messages = greenMail.receivedMessages
            messages.first().let { GreenMailUtil.getBody(it) } shouldBe "http://localhost/auth?code=$code"
        }
    }
})