package com.uramnoil.serverist.infrastructure.auth.unauthenticated.application.services

import com.uramnoil.serverist.application.auth.unauthenticated.service.SendEmailToAuthenticateService
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSenderImpl

class SpringBootSendEmailToAuthenticateService(
    private val host: String,
    private val port: Int,
    private val username: String,
    private val password: String,
    private val from: String,
    private val url: String
) : SendEmailToAuthenticateService {
    override suspend fun execute(email: String, token: String): Result<Unit> = kotlin.runCatching {
        val mailSender: MailSender = JavaMailSenderImpl().apply {
            host = this@SpringBootSendEmailToAuthenticateService.host
            port = this@SpringBootSendEmailToAuthenticateService.port
            username = this@SpringBootSendEmailToAuthenticateService.username
            password = this@SpringBootSendEmailToAuthenticateService.password
        }

        val message = SimpleMailMessage().apply {
            from = this@SpringBootSendEmailToAuthenticateService.from
            setTo(email)
            subject = "Serveristユーザー登録"
            text = "$url?token=${token}"
        }

        mailSender.send(message)
    }
}