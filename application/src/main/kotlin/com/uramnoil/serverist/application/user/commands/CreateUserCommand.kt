package com.uramnoil.serverist.application.user.commands

fun interface CreateUserCommand {
    fun execute(dto: CreateUserDto)
}

data class CreateUserDto(
    val accountId: String,
    val email: String,
    val hashedPassword: String,
    val name: String,
    val description: String
)
