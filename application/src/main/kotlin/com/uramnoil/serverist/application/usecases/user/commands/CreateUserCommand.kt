package com.uramnoil.serverist.application.usecases.user.commands

interface CreateUserCommand {
    data class CreateUserDto(val name: String, val description: String)

    fun execute(dto: CreateUserDto)
}