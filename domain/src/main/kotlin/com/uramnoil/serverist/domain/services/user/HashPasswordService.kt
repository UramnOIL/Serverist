package com.uramnoil.serverist.domain.services.user

import com.uramnoil.serverist.domain.models.user.HashedPassword

interface HashPasswordService {
    fun hash(password: String): HashedPassword
}