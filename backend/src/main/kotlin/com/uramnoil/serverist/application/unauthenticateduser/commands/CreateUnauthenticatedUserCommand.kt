package com.uramnoil.serverist.application.unauthenticateduser.commands

data class CreateUnauthenticatedUserDto(
    val accountId: String,
    val email: String,
    val hashedPassword: com.uramnoil.serverist.domain.kernel.models.Password
)

interface CreateUnauthenticatedUser {
    suspend fun execute(dto: CreateUnauthenticatedUserDto)
}