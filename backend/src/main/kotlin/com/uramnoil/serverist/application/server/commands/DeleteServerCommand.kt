package com.uramnoil.serverist.application.server.commands

import java.util.*

interface DeleteServerCommand {
    suspend fun execute(dto: DeleteServerDto)
}

data class DeleteServerDto(val id: UUID)
