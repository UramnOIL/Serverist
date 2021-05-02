package com.uramnoil.serverist.application.user.commands

import java.util.*

data class DeleteUserCommandDto(val id: UUID)

fun interface DeleteUserCommand {
    fun execute(dto: DeleteUserCommandDto)
}
