package com.uramnoil.serverist.application.user

import com.benasher44.uuid.Uuid

data class User(
    val id: Uuid,
    val accountId: String,
    val name: String,
    val description: String
)
