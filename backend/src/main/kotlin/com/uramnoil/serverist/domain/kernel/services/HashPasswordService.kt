package com.uramnoil.serverist.domain.kernel.services

interface HashPasswordService {
    fun hash(password: com.uramnoil.serverist.domain.kernel.models.Password): String
    fun check(
        password: com.uramnoil.serverist.domain.kernel.models.Password,
        hashedPassword: com.uramnoil.serverist.domain.kernel.models.HashedPassword
    ): Boolean
}