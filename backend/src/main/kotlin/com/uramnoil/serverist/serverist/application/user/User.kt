package com.uramnoil.serverist.serverist.application.user

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class User(
    @Contextual
    val id: UUID,
    val accountId: String,
    val name: String,
    val description: String
)
