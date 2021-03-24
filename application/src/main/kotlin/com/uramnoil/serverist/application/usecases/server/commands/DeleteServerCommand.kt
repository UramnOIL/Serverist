package com.uramnoil.serverist.application.usecases.server.commands

import java.util.*

fun interface DeleteServerCommand {
    fun execute(dto: DeleteServerDto)
}

data class DeleteServerDto(val id: UUID)
