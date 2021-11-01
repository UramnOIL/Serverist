package com.uramnoil.serverist.application.auth.unauthenticated.queries

import com.benasher44.uuid.Uuid
import kotlinx.datetime.Instant

data class User(
    val id: Uuid,
    val email: String,
    val hashedPassword: String,
    val expiredAt: Instant
)
