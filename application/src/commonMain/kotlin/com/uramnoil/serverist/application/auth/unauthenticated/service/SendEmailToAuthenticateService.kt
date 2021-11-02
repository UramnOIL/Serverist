package com.uramnoil.serverist.application.auth.unauthenticated.service

interface SendEmailToAuthenticateService {
    fun execute(email: String, token: String): Result<Unit>
}