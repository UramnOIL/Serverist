package com.uramnoil.serverist.application.server.commands

import java.util.*

data class UpdateServerCommandDto(
    val id: UUID,
    val name: String,
    val address: String?,
    val port: Int?,
    val description: String
)

interface UpdateServerCommand {
    suspend fun execute(dto: UpdateServerCommandDto)
}