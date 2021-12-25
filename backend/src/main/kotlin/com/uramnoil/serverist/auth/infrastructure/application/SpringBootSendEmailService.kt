package com.uramnoil.serverist.auth.infrastructure.application

import com.uramnoil.serverist.auth.application.SendEmailService
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSenderImpl

class SpringBootSendEmailService(
    private val host: String,
    private val port: Int,
    private val username: String,
    private val password: String,
    private val from: String,
    private val activateUrl: String,
) : SendEmailService {
    override suspend fun sendActivationEmail(mailAddress: String, activationCode: String): Result<Unit> {
        val mailSender: MailSender = JavaMailSenderImpl().apply {
            host = this@SpringBootSendEmailService.host
            port = this@SpringBootSendEmailService.port
            username = this@SpringBootSendEmailService.username
            password = this@SpringBootSendEmailService.password
        }

        val message = SimpleMailMessage().apply {
            from = this@SpringBootSendEmailService.from
            setTo(mailAddress)
            subject = "Serveristユーザー登録"
            text = "$activateUrl?code=$activationCode"
        }

        return kotlin.runCatching {
            mailSender.send(message)
        }
    }
}
