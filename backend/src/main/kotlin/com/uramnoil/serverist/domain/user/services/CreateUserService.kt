package com.uramnoil.serverist.domain.user.services

import com.uramnoil.serverist.domain.kernel.models.HashedPassword
import com.uramnoil.serverist.domain.user.models.*

data class CreateUserServiceDto(
    val accountId: AccountId,
    val email: Email,
    val hashedPassword: HashedPassword,
    val name: Name,
    val description: Description
)

interface CreateUserService {
    suspend fun new(dto: CreateUserServiceDto): User
}