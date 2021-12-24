package com.uramnoil.serverist.serverist.infrastructure.application.user

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class User(
    @Contextual
    val id: UUID,
    val accountId: String,
    val name: String,
    val description: String
)
