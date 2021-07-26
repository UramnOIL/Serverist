package com.uramnoil.serverist.application.unauthenticateduser.service

import com.uramnoil.serverist.application.unauthenticateduser.UnauthenticatedUser

actual interface SendEmailToAuthenticateService {
    fun execute(user: UnauthenticatedUser)
}

interface SendEmailToAuthenticateServiceOutputPort {
    fun handle(result: Result<Unit>)
}