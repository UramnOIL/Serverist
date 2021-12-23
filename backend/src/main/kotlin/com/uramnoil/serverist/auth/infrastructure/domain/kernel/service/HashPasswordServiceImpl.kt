package com.uramnoil.serverist.auth.infrastructure.domain.kernel.service

import com.uramnoil.serverist.domain.auth.kernel.model.HashedPassword
import com.uramnoil.serverist.domain.auth.kernel.model.Password
import com.uramnoil.serverist.domain.auth.kernel.services.HashPasswordService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class HashPasswordServiceImpl : HashPasswordService {
    private val encoder = BCryptPasswordEncoder()

    override fun hash(password: Password) = HashedPassword(encoder.encode(password.value))

    override fun check(
        password: Password,
        hashedPassword: HashedPassword
    ) = encoder.matches(password.value, hashedPassword.value)
}
