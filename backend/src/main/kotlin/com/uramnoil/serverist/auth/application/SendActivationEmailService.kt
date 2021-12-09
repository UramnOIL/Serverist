package com.uramnoil.serverist.auth.application

import java.util.*

interface SendActivationEmailService {
    suspend fun sendEmail(mailAddress: String, activationCode: UUID): Result<Unit>
}