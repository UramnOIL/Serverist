package com.uramnoil.serverist.domain.services.user

import com.uramnoil.serverist.domain.models.user.User
import com.uramnoil.serverist.domain.services.Password

fun interface UpdatePasswordService {
    fun update(user: User, password: Password)
}