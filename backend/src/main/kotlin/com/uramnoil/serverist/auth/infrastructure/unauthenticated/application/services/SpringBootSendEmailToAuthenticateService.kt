package com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.services

import com.uramnoil.serverist.auth.application.unauthenticated.service.SendEmailToAuthenticateUseCase
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.*

class SpringBootSendEmailToAuthenticateService(
    private val host: String,
    private val port: Int,
    private val username: String,
    private val password: String,
    private val from: String,
    private val activateUrl: String
) : SendEmailToAuthenticateUseCase {
    override fun execute(email: String, activationCode: UUID): Result<Unit> = kotlin.runCatching {
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
            text = "$activateUrl?code=${activationCode}"
        }

        mailSender.send(message)
    }
}