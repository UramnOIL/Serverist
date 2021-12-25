package com.uramnoil.serverist.backend.auth.infrastructure.unauthenticated.services

import com.icegreen.greenmail.util.GreenMail
import com.icegreen.greenmail.util.ServerSetupTest
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSenderImpl

class SpringBootStarterMailTest : FunSpec({
    val greenMail = GreenMail(ServerSetupTest.SMTP)

    beforeTest {
        greenMail.start()
    }

    afterTest {
        greenMail.stop()
    }

    test("SpringBootStarterMailのお試し") {
        val mailSender: MailSender = JavaMailSenderImpl().apply {
            host = "localhost"
            port = 3025
        }

        val from = "test@serverist.com"
        val to = "hoge@serverist.com"

        val message = SimpleMailMessage().apply {
            setFrom(from)
            setTo(to)
            subject = "テスト件名"
            text = "テスト本文"
        }

        mailSender.send(message)

        greenMail.waitForIncomingEmail(3000, 1) shouldBe true

        val mails = greenMail.receivedMessages
        mails.isNotEmpty() shouldBe true
    }
})
