package com.uramnoil.serverist.domain.user.services

import com.uramnoil.serverist.domain.user.model.unapproveduser.User

interface SendMessageToValidateEmailService {
    fun sendMessage(user: User)
}