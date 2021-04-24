package com.uramnoil.serverist.application.service.usecases.user.commands

fun interface CreateUserCommand {
    fun execute(dto: CreateUserDto)
}

data class CreateUserDto(
    val accountId: String,
    val email: String,
    val password: String,
    val name: String,
    val description: String
)
