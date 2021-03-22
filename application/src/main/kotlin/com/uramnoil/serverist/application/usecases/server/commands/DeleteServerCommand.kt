package com.uramnoil.serverist.application.usecases.server.commands

interface DeleteServerCommand {
    fun execute(dto: DeleteServerDto)
}

data class DeleteServerDto(val id: String)
