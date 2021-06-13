package com.uramnoil.serverist.backend

import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSenderImpl
import kotlin.test.Test

class SpringBootStarterMailTest {
    @Test
    fun `mail4devに接続する`() {
        val mailSender: MailSender = JavaMailSenderImpl().apply {
            host = "localhost"
            port = 25
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
    }
}