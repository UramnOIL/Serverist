package com.uramnoil.serverist.domain.models.kernel.services

import com.uramnoil.serverist.domain.models.kernel.models.HashedPassword
import com.uramnoil.serverist.domain.models.kernel.models.Password

interface HashPasswordService {
    fun hash(password: Password): String
    fun check(password: Password, hashedPassword: HashedPassword): Boolean
}