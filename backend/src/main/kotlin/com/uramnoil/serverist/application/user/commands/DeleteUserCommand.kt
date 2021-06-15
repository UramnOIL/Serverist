package com.uramnoil.serverist.application.user.commands

import java.util.*

data class DeleteUserCommandDto(val id: UUID)

interface DeleteUserCommand {
    suspend fun execute(dto: DeleteUserCommandDto)
}
