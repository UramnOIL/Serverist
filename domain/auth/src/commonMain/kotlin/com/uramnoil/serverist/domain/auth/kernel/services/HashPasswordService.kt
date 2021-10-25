package com.uramnoil.serverist.domain.auth.kernel.services

import com.uramnoil.serverist.domain.auth.kernel.model.HashedPassword
import com.uramnoil.serverist.domain.auth.kernel.model.Password

interface HashPasswordService {
    fun hash(password: Password): HashedPassword
    fun check(
        password: Password, hashedPassword: HashedPassword
    ): Boolean
}