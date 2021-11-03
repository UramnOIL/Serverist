package com.uramnoil.serverist.serverist.server.application

import com.benasher44.uuid.Uuid
import kotlinx.datetime.Instant

data class Server(
    val id: Uuid,
    val createdAt: Instant,
    val ownerId: Uuid,
    val name: String,
    val address: String?,
    val port: Int?,
    val description: String
)