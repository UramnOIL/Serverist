package com.uramnoil.serverist.application.auth.unauthenticated.service

interface SendEmailToAuthenticateService {
    suspend fun execute(email: String, token: String): Result<Unit>
}