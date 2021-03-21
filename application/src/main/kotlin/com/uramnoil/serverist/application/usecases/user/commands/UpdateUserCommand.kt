package com.uramnoil.serverist.application.usecases.user.commands

interface UpdateUserCommand {
    data class UpdateUserDto(val id: String, val name: String, val description: String)

    fun execute(dto: UpdateUserDto)
}