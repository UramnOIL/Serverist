package com.uramnoil.serverist.domain.services.user

interface HashPasswordService {
    fun execute(password: String): String
}