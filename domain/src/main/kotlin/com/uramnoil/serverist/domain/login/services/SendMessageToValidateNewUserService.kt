package com.uramnoil.serverist.domain.login.services

import com.uramnoil.serverist.domain.login.model.unapproveduser.User

interface SendMessageToValidateEmailService {
    fun sendMessage(user: User)
}