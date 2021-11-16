package com.uramnoil.serverist.application.unauthenticated.service

import java.util.*

interface SendEmailToAuthenticateUseCase {
    fun execute(email: String, activationCode: UUID): Result<Unit>
}