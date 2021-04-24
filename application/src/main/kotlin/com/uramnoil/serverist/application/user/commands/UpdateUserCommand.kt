package com.uramnoil.serverist.application.user.commands

import java.util.*

fun interface UpdateUserCommand {
    fun execute(dto: UpdateUserDto)
}

data class UpdateUserDto(val id: UUID, val name: String, val description: String)
