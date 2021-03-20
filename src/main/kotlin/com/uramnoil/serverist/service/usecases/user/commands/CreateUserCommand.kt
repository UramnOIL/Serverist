package com.uramnoil.serverist.service.usecases.user.commands

interface CreateUserCommand {
    data class CreateUserDto(val name: String, val description: String)

    fun execute(dto: CreateUserDto)
}