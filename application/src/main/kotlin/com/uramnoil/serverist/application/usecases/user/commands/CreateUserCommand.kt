package com.uramnoil.serverist.application.usecases.user.commands

fun interface CreateUserCommand {
    fun execute(dto: CreateUserDto)
}

data class CreateUserDto(val name: String, val description: String)
