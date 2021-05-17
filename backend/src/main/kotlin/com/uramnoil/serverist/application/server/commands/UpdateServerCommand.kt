package com.uramnoil.serverist.application.server.commands

interface UpdateServerCommand {
    suspend fun execute(dto: UpdateServerDto)
}

data class UpdateServerDto(
    val id: String,
    val name: String,
    val address: String?,
    val port: Int?,
    val description: String
)