package com.uramnoil.serverist.domain.services.password

interface HashPasswordService {
    fun hash(password: String): String
}