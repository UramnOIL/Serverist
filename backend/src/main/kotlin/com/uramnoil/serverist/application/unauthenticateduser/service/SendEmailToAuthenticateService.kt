package com.uramnoil.serverist.application.unauthenticateduser.service

import com.uramnoil.serverist.domain.unauthenticateduser.models.UnauthenticatedUser

interface SendEmailToAuthenticateService {
    fun execute(user: UnauthenticatedUser)
}