package com.uramnoil.serverist.application.unauthenticateduser.commands

import com.uramnoil.serverist.domain.services.user.Password

data class CreateUnauthenticatedUserDto(val email: String, val hashedPassword: Password)

interface CreateUnauthenticatedUser {
    suspend fun execute(dto: CreateUnauthenticatedUserDto)
}