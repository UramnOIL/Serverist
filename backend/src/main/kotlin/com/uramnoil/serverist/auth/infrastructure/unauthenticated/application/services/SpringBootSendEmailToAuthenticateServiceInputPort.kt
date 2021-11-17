package com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.services

import com.uramnoil.serverist.application.unauthenticated.service.SendEmailToAuthenticateUseCaseInputPort
import com.uramnoil.serverist.application.unauthenticated.service.SendEmailToAuthenticateUseCaseOutputPort
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.*
import kotlin.coroutines.CoroutineContext

class SpringBootSendEmailToAuthenticateServiceInputPort(
    private val host: String,
    private val port: Int,
    private val username: String,
    private val password: String,
    private val from: String,
    private val activateUrl: String,
    private val outputPort: SendEmailToAuthenticateUseCaseOutputPort,
    private val coroutineContext: CoroutineContext,
) : SendEmailToAuthenticateUseCaseInputPort {
    override fun execute(email: String, activationCode: UUID) {
        kotlin.runCatching {
            CoroutineScope(coroutineContext).launch {
                val mailSender: MailSender = JavaMailSenderImpl().apply {
                    host = this@SpringBootSendEmailToAuthenticateServiceInputPort.host
                    port = this@SpringBootSendEmailToAuthenticateServiceInputPort.port
                    username = this@SpringBootSendEmailToAuthenticateServiceInputPort.username
                    password = this@SpringBootSendEmailToAuthenticateServiceInputPort.password
                }

                val message = SimpleMailMessage().apply {
                    from = this@SpringBootSendEmailToAuthenticateServiceInputPort.from
                    setTo(email)
                    subject = "Serveristユーザー登録"
                    text = "$activateUrl?code=${activationCode}"
                }

                mailSender.send(message)
                outputPort.handle(Result.success(Unit))
            }
        }
    }
}