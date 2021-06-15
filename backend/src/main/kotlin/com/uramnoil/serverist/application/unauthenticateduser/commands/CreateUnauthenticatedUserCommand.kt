package com.uramnoil.serverist.application.unauthenticateduser.commands

import com.uramnoil.serverist.domain.models.kernel.models.Password

data class CreateUnauthenticatedUserDto(val accountId: String, val email: String, val hashedPassword: Password)

interface CreateUnauthenticatedUser {
    suspend fun execute(dto: CreateUnauthenticatedUserDto)
}