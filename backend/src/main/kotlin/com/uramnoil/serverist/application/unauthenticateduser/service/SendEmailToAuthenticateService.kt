package com.uramnoil.serverist.application.unauthenticateduser.service

import com.uramnoil.serverist.application.unauthenticateduser.User

interface SendEmailToAuthenticateService {
    fun execute(user: User)
}