package com.uramnoil.serverist.application.usecases.server.commands

fun interface DeleteServerCommand {
    fun execute(dto: DeleteServerDto)
}

data class DeleteServerDto(val id: String)
