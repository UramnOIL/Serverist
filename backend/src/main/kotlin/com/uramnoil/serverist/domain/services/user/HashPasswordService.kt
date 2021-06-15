package com.uramnoil.serverist.domain.services.user

interface HashPasswordService {
    fun hash(row: String): String
    fun check(row: String, hash: String): Boolean
}