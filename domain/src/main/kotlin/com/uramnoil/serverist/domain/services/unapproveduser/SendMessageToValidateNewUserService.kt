package com.uramnoil.serverist.domain.services.unapproveduser

import com.uramnoil.serverist.domain.models.unapproveduser.User

interface SendMessageToValidateEmailService {
    fun sendMessage(user: User)
}