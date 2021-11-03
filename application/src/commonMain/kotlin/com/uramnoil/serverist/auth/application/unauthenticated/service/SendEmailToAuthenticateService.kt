package com.uramnoil.serverist.auth.application.unauthenticated.service

interface SendEmailToAuthenticateService {
    fun execute(email: String, token: String): Result<Unit>
}