package com.uramnoil.serverist.application.login.usecase.user.commands

data class CreateUserCommandDto(val id: String, val email: String, val password: String, val name: String)

interface CreateUserCommand {
    fun execute(dto: CreateUserCommandDto)
}