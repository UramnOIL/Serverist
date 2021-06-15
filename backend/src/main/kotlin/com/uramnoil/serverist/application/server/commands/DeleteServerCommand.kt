package com.uramnoil.serverist.application.server.commands

import java.util.*

data class DeleteServerCommandDto(val id: UUID)

interface DeleteServerCommand {
    suspend fun execute(dto: DeleteServerCommandDto)
}
