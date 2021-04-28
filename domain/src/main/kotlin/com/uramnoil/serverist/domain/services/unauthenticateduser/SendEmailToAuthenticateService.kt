package com.uramnoil.serverist.domain.services.unauthenticateduser

import com.uramnoil.serverist.domain.models.unauthenticateduser.UnauthenticatedUser

interface SendEmailToAuthenticateService {
    fun sendTo(user: UnauthenticatedUser)
}