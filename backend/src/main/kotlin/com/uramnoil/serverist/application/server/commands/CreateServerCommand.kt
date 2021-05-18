package com.uramnoil.serverist.application.server.commands

import java.util.*

interface CreateServerCommand {
    suspend fun execute(dto: CreateServerCommandDto)
}

data class CreateServerCommandDto(
    val ownerId: UUID,
    val name: String,
    val address: String?,
    val port: Int?,
    val description: String
)