package com.uramnoil.serverist.domain.login.services

import com.uramnoil.serverist.domain.login.models.unapproveduser.User

interface SendMessageToValidateEmailService {
    fun sendMessage(user: User)
}