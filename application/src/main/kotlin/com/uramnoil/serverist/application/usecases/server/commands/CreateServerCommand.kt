package com.uramnoil.serverist.application.usecases.server.commands

interface CreateServerCommand {
    fun execute(dto: CreateServerDto)
}

data class CreateServerDto(
    val ownerId: String,
    val name: String,
    val address: String?,
    val port: Int?,
    val description: String?
)