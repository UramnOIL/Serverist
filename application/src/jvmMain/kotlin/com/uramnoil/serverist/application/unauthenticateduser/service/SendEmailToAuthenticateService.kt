package com.uramnoil.serverist.application.unauthenticateduser.service

import com.uramnoil.serverist.application.unauthenticateduser.UnauthenticatedUser

actual interface SendEmailToAuthenticateService {
    fun execute(user: UnauthenticatedUser): Result<Unit>
}