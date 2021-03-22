package com.uramnoil.serverist.application.usecases.user.commands

fun interface DeleteUserCommand {
    fun execute(dto: DeleteUserCommand)
}

data class DeleteUserCommandDto(val id: String)
