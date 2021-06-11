package com.uramnoil.serverist.application.unauthenticateduser.commands

import java.util.*

data class DeleteUnauthenticatedUserCommandDto(val id: UUID)

interface DeleteUnauthenticatedUserCommand {
    suspend fun execute(dto: DeleteUnauthenticatedUserCommandDto)
}