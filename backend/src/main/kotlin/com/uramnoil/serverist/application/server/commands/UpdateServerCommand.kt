package com.uramnoil.serverist.application.server.commands

data class UpdateServerCommandDto(
    val id: String,
    val name: String,
    val address: String?,
    val port: Int?,
    val description: String
)

interface UpdateServerCommand {
    suspend fun execute(dto: UpdateServerCommandDto)
}