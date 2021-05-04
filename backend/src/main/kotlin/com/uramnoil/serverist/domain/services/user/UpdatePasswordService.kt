package com.uramnoil.serverist.domain.services.user

import com.uramnoil.serverist.domain.models.user.User

fun interface UpdatePasswordService {
    fun update(user: User, password: Password)
}