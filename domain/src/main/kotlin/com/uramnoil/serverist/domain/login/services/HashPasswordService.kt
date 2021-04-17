package com.uramnoil.serverist.domain.login.services

interface HashPasswordService {
    fun hash(password: String)
}