package com.uramnoil.serverist.infrastracture

import com.uramnoil.serverist.domain.kernel.models.user.HashedPassword
import com.uramnoil.serverist.domain.kernel.models.user.Password
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class HashPasswordServiceImpl : com.uramnoil.serverist.domain.kernel.services.HashPasswordService {
    private val encoder = BCryptPasswordEncoder()

    override fun hash(password: Password): HashedPassword = HashedPassword(encoder.encode(password.value))

    override fun check(
        password: Password,
        hashedPassword: HashedPassword
    ): Boolean =
        encoder.matches(password.value, hashedPassword.value)
}