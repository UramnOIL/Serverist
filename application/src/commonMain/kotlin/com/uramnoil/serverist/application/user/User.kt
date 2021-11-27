package com.uramnoil.serverist.application.user

import com.benasher44.uuid.Uuid
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Uuid,
    val accountId: String,
    val name: String,
    val description: String
)
