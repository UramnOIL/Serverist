package com.uramnoil.serverist.application.usecases.user.commands

import java.util.*

fun interface DeleteUserCommand {
    fun execute(dto: DeleteUserCommandDto)
}

data class DeleteUserCommandDto(val id: UUID)
