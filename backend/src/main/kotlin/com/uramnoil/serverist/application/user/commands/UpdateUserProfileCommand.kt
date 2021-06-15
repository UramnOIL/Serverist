package com.uramnoil.serverist.application.user.commands

import java.util.*

data class UpdateUserProfileCommandDto(val id: UUID, val name: String, val description: String)

interface UpdateUserProfileCommand {
    suspend fun execute(dto: UpdateUserProfileCommandDto)
}
