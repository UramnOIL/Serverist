package com.uramnoil.serverist.application.user.commands

import java.util.*

data class UpdateUserDto(val id: UUID, val name: String, val description: String)

fun interface UpdateUserCommand {
    fun execute(dto: UpdateUserDto)
}
