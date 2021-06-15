package com.uramnoil.serverist.application.unauthenticateduser.service

import com.uramnoil.serverist.domain.models.unauthenticateduser.models.UnauthenticatedUser

interface SendEmailToAuthenticateService {
    fun sendTo(user: UnauthenticatedUser)
}