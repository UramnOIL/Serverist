package com.uramnoil.serverist.domain.kernel.services

import com.uramnoil.serverist.domain.kernel.models.user.HashedPassword
import com.uramnoil.serverist.domain.kernel.models.user.Password

interface HashPasswordService {
    fun hash(password: Password): HashedPassword
    fun check(
        password: Password,
        hashedPassword: HashedPassword
    ): Boolean
}