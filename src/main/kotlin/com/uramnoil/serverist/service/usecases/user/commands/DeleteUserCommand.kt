package com.uramnoil.serverist.service.usecases.user.commands

interface DeleteUserCommand {
    data class DeleteUserCommand(val id: String)

    fun execute(dto: DeleteUserCommand)
}