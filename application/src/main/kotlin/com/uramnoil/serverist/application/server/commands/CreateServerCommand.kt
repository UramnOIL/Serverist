package com.uramnoil.serverist.application.server.commands

import java.util.*

fun interface CreateServerCommand {
    fun execute(dto: CreateServerDto)
}

data class CreateServerDto(
    val ownerId: UUID,
    val name: String,
    val address: String?,
    val port: Int?,
    val description: String
)