package com.uramnoil.serverist.application.unauthenticateduser

import com.benasher44.uuid.Uuid

data class UnauthenticatedUser(
    val id: Uuid,
    val accountId: String,
    val email: String
)
