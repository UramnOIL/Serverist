package com.uramnoil.serverist.application.usecases.user.commands

interface DeleteUserCommand {
    data class DeleteUserCommand(val id: String)

    fun execute(dto: DeleteUserCommand)
}