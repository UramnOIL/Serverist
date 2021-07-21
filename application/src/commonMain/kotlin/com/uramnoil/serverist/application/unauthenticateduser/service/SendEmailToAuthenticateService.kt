package com.uramnoil.serverist.application.unauthenticateduser.service

import com.uramnoil.serverist.application.unauthenticateduser.UnauthenticatedUser

interface SendEmailToAuthenticateService {
    fun execute(user: UnauthenticatedUser)
}