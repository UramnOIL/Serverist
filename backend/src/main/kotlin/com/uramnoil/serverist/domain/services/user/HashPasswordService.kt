package com.uramnoil.serverist.domain.services.user

interface HashPasswordService {
    fun hash(password: String): String
    fun check(password: String, hash: String): Boolean
}