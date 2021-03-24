package com.uramnoil.serverist.application.usecases.user.commands

import java.util.*

fun interface DeleteUserCommand {
    fun execute(dto: DeleteUserCommand)
}

data class DeleteUserCommandDto(val id: UUID)
