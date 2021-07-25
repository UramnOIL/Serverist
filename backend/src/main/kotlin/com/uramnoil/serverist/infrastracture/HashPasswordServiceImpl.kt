package com.uramnoil.serverist.infrastracture

import com.uramnoil.serverist.domain.kernel.models.user.HashedPassword
import com.uramnoil.serverist.domain.kernel.models.user.Password
import com.uramnoil.serverist.domain.kernel.services.HashPasswordService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class HashPasswordServiceImpl : HashPasswordService {
    private val encoder = BCryptPasswordEncoder()

    override fun hash(password: Password) = HashedPassword(encoder.encode(password.value))

    override fun check(
        password: Password,
        hashedPassword: HashedPassword
    ) = encoder.matches(password.value, hashedPassword.value)
}