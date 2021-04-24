package com.uramnoil.serverist.application.user.commands

import java.util.*

fun interface DeleteUserCommand {
    fun execute(dto: DeleteUserCommandDto)
}

data class DeleteUserCommandDto(val id: UUID)
