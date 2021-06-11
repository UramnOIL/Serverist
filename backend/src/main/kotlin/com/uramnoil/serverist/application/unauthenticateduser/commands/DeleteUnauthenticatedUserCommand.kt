package com.uramnoil.serverist.application.unauthenticateduser.commands

data class DeleteUnauthenticatedUserCommandDto(val id: String)

interface DeleteUnauthenticatedUserCommand {
    suspend fun execute(dto: DeleteUnauthenticatedUserCommandDto)
}