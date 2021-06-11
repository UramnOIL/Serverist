package com.uramnoil.serverist.application.unauthenticateduser

import java.util.*

data class User(
    val id: UUID,
    val accountId: String,
    val email: String,
    val hashedPassword: String,
)
