package com.uramnoil.serverist.application.server

import java.util.*

data class User(
    val id: UUID,
    val accountId: String,
    val name: String,
    val description: String
)
