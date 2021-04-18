package com.uramnoil.serverist.application.service.usecases.server.commands

import java.util.*

fun interface DeleteServerCommand {
    fun execute(dto: DeleteServerDto)
}

data class DeleteServerDto(val id: UUID)
