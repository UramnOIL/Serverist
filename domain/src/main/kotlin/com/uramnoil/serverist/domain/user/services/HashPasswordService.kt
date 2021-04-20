package com.uramnoil.serverist.domain.user.services

interface HashPasswordService {
    fun hash(password: String)
}