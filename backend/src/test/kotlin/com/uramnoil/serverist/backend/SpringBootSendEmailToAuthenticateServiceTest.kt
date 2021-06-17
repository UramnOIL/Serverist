package com.uramnoil.serverist.backend

import com.uramnoil.serverist.application.unauthenticateduser.User
import com.uramnoil.serverist.infrastracture.unapproveduser.SpringBootSendEmailToAuthenticateService
import java.util.*
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

        service.execute(
            User(
                id = UUID.randomUUID(),
                accountId = "test",
                email = "hoge.com",
                hashedPassword = "fuga"
            )
        )
    }
}