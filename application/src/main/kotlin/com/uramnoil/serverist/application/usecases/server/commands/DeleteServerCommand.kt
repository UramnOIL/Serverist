package com.uramnoil.serverist.application.usecases.server.commands

interface DeleteServerCommand {
    data class DeleteServerCommand(val id: String)

    fun execute(dto: DeleteServerCommand)
}