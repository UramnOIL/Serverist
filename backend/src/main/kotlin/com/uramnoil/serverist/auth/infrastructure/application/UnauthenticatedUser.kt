package com.uramnoil.serverist.auth.infrastructure.application

import com.benasher44.uuid.Uuid

data class UnauthenticatedUser(
    val id: Uuid,
    val email: String,
    val hashedPassword: String,
    val activationCode: Int
)
