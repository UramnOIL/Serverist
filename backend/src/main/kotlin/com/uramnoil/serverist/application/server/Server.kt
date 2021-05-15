package com.uramnoil.serverist.application.server

import kotlinx.datetime.Instant
import java.util.*

data class Server(
    val id: UUID,
    val createdAt: Instant,
    val ownerId: UUID,
    val name: String,
    val address: String?,
    val port: Int?,
    val description: String
)
