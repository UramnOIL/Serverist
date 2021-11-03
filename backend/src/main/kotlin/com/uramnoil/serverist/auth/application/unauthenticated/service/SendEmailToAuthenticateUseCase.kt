package com.uramnoil.serverist.auth.application.unauthenticated.service

interface SendEmailToAuthenticateUseCase {
    suspend fun execute(email: String, token: String): Result<Unit>
}