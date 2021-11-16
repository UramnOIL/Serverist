package com.uramnoil.serverist.application.unauthenticated.queries

import java.util.*

data class User(
    val id: UUID,
    val email: String,
    val hashedPassword: String,
    val activationCode: UUID
)
