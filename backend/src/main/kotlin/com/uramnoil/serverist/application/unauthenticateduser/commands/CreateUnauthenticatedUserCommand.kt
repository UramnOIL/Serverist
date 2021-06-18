package com.uramnoil.serverist.application.unauthenticateduser.commands

import com.uramnoil.serverist.application.unauthenticateduser.User

data class CreateUnauthenticatedUserCommandDto(
    val accountId: String,
    val email: String,
    val hashedPassword: String
)

interface CreateUnauthenticatedUserCommand {
    suspend fun execute(dto: CreateUnauthenticatedUserCommandDto): User
}