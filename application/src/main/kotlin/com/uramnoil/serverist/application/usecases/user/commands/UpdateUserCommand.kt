package com.uramnoil.serverist.application.usecases.user.commands

fun interface UpdateUserCommand {
    fun execute(dto: UpdateUserDto)
}

data class UpdateUserDto(val id: String, val name: String, val description: String)
