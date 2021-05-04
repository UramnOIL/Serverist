package com.uramnoil.serverist.application.user.commands

data class CreateUserDto(
    val accountId: String,
    val email: String,
    val hashedPassword: String,
    val name: String,
    val description: String
)

fun interface CreateUserCommand {
    fun execute(dto: CreateUserDto)
}