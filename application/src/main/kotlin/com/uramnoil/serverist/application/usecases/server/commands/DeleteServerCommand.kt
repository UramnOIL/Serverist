package com.uramnoil.serverist.application.usecases.server.commands

interface DeleteServerCommand {
    data class DeleteServerDto(val id: String)

    fun execute(dto: DeleteServerDto)
}