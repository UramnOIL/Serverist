package com.uramnoil.serverist.application.usecases.user.commands

interface DeleteUserCommand {
    fun execute(dto: DeleteUserCommand)
}

data class DeleteUserCommand(val id: String)
