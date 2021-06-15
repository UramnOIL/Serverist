package com.uramnoil.serverist.infrastracture

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class HashPasswordServiceImpl : com.uramnoil.serverist.domain.kernel.services.HashPasswordService {
    private val encoder = BCryptPasswordEncoder()

    override fun hash(password: com.uramnoil.serverist.domain.kernel.models.Password): String =
        encoder.encode(password.value)

    override fun check(
        password: com.uramnoil.serverist.domain.kernel.models.Password,
        hashedPassword: com.uramnoil.serverist.domain.kernel.models.HashedPassword
    ): Boolean =
        encoder.matches(password.value, hashedPassword.value)
}