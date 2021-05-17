package com.uramnoil.serverist.infrastracture

import com.uramnoil.serverist.domain.services.user.HashPasswordService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class HashPasswordServiceImpl : HashPasswordService {
    val encoder = BCryptPasswordEncoder()

    override fun hash(row: String): String = encoder.encode(row)

    override fun check(row: String, hash: String): Boolean = encoder.matches(row, hash)
}