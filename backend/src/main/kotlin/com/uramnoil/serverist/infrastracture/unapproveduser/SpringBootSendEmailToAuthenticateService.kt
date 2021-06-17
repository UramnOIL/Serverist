package com.uramnoil.serverist.infrastracture.unapproveduser

import com.uramnoil.serverist.application.unauthenticateduser.User
import com.uramnoil.serverist.application.unauthenticateduser.service.SendEmailToAuthenticateService
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
    override fun execute(user: User) {
        val mailSender: MailSender = JavaMailSenderImpl().apply {
            host = this@SpringBootSendEmailToAuthenticateService.host
            port = this@SpringBootSendEmailToAuthenticateService.port
            username = this@SpringBootSendEmailToAuthenticateService.username
            password = this@SpringBootSendEmailToAuthenticateService.password
        }

        val message = SimpleMailMessage().apply {
            from = this@SpringBootSendEmailToAuthenticateService.from
            setTo(user.email)
            subject = "Serveristユーザー登録"
            text = """
                ${user.accountId}
                $url?token=${user.id}
            """
        }

        mailSender.send(message)
    }
}