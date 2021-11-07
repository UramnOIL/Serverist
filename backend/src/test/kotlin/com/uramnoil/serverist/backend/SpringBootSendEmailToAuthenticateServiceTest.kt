package com.uramnoil.serverist.backend

import com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.services.SpringBootSendEmailToAuthenticateService
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class SpringBootSendEmailToAuthenticateServiceTest {
    @Test
    fun `送信テスト`() {
        val service = SpringBootSendEmailToAuthenticateService(
            host = "localhost",
            port = 25,
            username = "",
            password = "",
            from = "test@serverist.com",
            url = "http://localhost/auth",
        )

        val code = UUID.randomUUID()

        runBlocking {
            service.execute("hoge.com", "token")
        }
    }
}