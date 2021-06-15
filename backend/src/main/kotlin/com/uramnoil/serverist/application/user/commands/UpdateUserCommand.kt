package com.uramnoil.serverist.application.user.commands

import java.util.*

data class UpdateUserCommandDto(val id: UUID, val name: String, val description: String)

interface UpdateUserCommand {
    suspend fun execute(dto: UpdateUserCommandDto)
}
