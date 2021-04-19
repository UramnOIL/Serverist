package com.uramnoil.serverist.application.login.usecase.unapproved.commands

data class CreateUserCommandDto(val id: String, val email: String, val password: String, val name: String)

interface CreateUserCommand {
    fun execute(dto: CreateUserCommandDto)
}