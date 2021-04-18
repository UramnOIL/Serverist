package com.uramnoil.serverist.application.service.usecases.user.commands

import java.util.*

fun interface DeleteUserCommand {
    fun execute(dto: DeleteUserCommandDto)
}

data class DeleteUserCommandDto(val id: UUID)
