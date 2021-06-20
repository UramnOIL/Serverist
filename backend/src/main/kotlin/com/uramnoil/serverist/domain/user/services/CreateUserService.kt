package com.uramnoil.serverist.domain.user.services

import com.uramnoil.serverist.domain.kernel.models.HashedPassword
import com.uramnoil.serverist.domain.user.models.*

interface CreateUserService {
    suspend fun new(
        accountId: AccountId,
        email: Email,
        hashedPassword: HashedPassword,
        name: Name,
        description: Description
    ): User
}