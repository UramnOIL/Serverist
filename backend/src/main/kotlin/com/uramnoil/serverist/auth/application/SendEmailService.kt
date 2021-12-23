package com.uramnoil.serverist.auth.application

import java.util.UUID

interface SendEmailService {
    suspend fun sendActivationEmail(mailAddress: String, activationCode: UUID): Result<Unit>
}
