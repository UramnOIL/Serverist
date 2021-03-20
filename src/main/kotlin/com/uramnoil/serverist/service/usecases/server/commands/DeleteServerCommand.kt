package com.uramnoil.serverist.service.usecases.server.commands

interface DeleteServerCommand {
    data class DeleteServerCommand(val id: String)

    fun invoke(dto: DeleteServerCommand)
}