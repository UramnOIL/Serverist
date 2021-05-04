package com.uramnoil.serverist.domain.services.user

interface HashPasswordService {
    fun hash(password: String): String
}