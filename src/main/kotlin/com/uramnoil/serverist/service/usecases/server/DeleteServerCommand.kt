package com.uramnoil.serverist.service.usecases.server

interface DeleteServerCommand {
    data class DeleteServerCommand(val id: String)

    fun invoke(dto: DeleteServerCommand)
}