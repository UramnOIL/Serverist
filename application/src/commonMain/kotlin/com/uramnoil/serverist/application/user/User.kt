package com.uramnoil.serverist.application.user

import java.util.*

data class User(
    val id: UUID,
    val accountId: String,
    val name: String,
    val description: String
)
