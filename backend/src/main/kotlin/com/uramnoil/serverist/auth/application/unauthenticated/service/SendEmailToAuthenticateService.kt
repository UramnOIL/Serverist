package com.uramnoil.serverist.auth.application.unauthenticated.service

interface SendEmailToAuthenticateService {
    suspend fun execute(email: String, token: String): Result<Unit>
}