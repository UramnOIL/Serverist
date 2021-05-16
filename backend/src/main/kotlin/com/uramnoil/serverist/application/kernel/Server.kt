package com.uramnoil.serverist.application.kernel

import com.uramnoil.serverist.application.server.User
import kotlinx.datetime.Instant
import java.util.*

data class Server(
    val id: UUID,
    val createdAt: Instant,
    val owner: User,
    val name: String,
    val address: String?,
    val port: Int?,
    val description: String
)
