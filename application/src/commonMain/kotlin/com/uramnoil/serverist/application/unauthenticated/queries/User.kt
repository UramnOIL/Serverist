package com.uramnoil.serverist.application.unauthenticated.queries

import com.benasher44.uuid.Uuid

data class User(
    val id: Uuid,
    val email: String,
    val hashedPassword: String,
    val activationCode: Uuid
)
