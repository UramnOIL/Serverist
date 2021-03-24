package com.uramnoil.serverist.application.usecases.server.commands

import java.util.*

fun interface UpdateServerCommand {
    fun execute(dto: UpdateServerDto)
}

data class UpdateServerDto(
    val id: UUID,
    val name: String,
    val address: String?,
    val port: Int?,
    val description: String
)