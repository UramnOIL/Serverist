package com.uramnoil.serverist.application.server.commands

fun interface UpdateServerCommand {
    fun execute(dto: UpdateServerDto)
}

data class UpdateServerDto(
    val id: String,
    val name: String,
    val address: String?,
    val port: Int?,
    val description: String
)