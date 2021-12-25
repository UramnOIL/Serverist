package com.uramnoil.serverist.auth.application

interface SendEmailService {
    suspend fun sendActivationEmail(mailAddress: String, activationCode: String): Result<Unit>
}
