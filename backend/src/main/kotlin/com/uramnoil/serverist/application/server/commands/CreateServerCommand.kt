package com.uramnoil.serverist.application.server.commands

import java.util.*

data class CreateServerCommandDto(
    val ownerId: UUID,
    val name: String,
    val address: String?,
    val port: Int?,
    val description: String
)

interface CreateServerCommand {
    suspend fun execute(dto: CreateServerCommandDto): UUID
}