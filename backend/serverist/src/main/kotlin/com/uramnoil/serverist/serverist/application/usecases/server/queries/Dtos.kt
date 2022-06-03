package com.uramnoil.serverist.serverist.application.usecases.server.queries

import java.util.UUID
import kotlinx.datetime.Instant

data class ServerDto(
    val id: UUID,
    val createdAt: Instant,
    val ownerId: UUID,
    val name: String,
    val host: String?,
    val port: Int?,
    val description: String
)

data class ServerWithOwnerDto(
    val id: UUID,
    val createdAt: Instant,
    val owner: UserDto,
    val name: String,
    val host: String?,
    val port: Int?,
    val description: String
)

data class UserDto(
    val id: String,
    val accountId: String,
    val name: String,
    val description: String,
    val iconId: String
)