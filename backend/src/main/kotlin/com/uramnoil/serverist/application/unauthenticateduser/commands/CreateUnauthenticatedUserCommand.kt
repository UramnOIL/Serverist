package com.uramnoil.serverist.application.unauthenticateduser.commands

data class CreateUnauthenticatedUserCommandDto(
    val accountId: String,
    val email: String,
    val password: String
)

interface CreateUnauthenticatedUserCommand {
    suspend fun execute(dto: CreateUnauthenticatedUserCommandDto)
}