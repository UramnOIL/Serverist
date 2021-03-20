package com.uramnoil.serverist.service.usecases.user.commands

interface UpdateUserCommand {
    data class UpdateUserDto(val id: String, val name: String, val description: String)

    fun execute(dto: UpdateUserDto)
}