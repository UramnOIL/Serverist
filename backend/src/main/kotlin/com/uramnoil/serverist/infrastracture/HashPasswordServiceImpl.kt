package com.uramnoil.serverist.infrastracture

import com.uramnoil.serverist.domain.models.kernel.models.HashedPassword
import com.uramnoil.serverist.domain.models.kernel.models.Password
import com.uramnoil.serverist.domain.models.kernel.services.HashPasswordService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class HashPasswordServiceImpl : HashPasswordService {
    private val encoder = BCryptPasswordEncoder()

    override fun hash(password: Password): String = encoder.encode(password.value)

    override fun check(password: Password, hashedPassword: HashedPassword): Boolean =
        encoder.matches(password.value, hashedPassword.value)
}