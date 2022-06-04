package com.uramnoil.serverist.application.server

import com.benasher44.uuid.Uuid
import kotlinx.datetime.Instant

data class Server(
    val id: Uuid,
    val createdAt: Instant,
    val ownerId: Uuid,
    val name: String,
    val host: String?,
    val port: Int?,
    val description: String
)
