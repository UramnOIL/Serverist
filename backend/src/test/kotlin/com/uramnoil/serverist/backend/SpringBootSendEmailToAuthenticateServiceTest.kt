package com.uramnoil.serverist.backend

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.unauthenticateduser.UnauthenticatedUser
import com.uramnoil.serverist.infrastracture.unauthenticateduser.SpringBootSendEmailToAuthenticateService
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
            UnauthenticatedUser(
                id = Uuid.randomUUID(),
                accountId = "test",
                email = "hoge.com"
            )
        )
    }
}