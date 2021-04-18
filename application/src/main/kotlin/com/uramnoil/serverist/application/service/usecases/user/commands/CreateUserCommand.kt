package com.uramnoil.serverist.application.service.usecases.user.commands

fun interface CreateUserCommand {
    fun execute(dto: CreateUserDto)
}

data class CreateUserDto(val name: String, val description: String)
