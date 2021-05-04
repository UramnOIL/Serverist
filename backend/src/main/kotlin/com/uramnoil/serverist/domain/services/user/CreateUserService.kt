package com.uramnoil.serverist.domain.services.user

import com.uramnoil.serverist.domain.models.user.*

interface CreateUserService {
    suspend fun new(
        accountId: AccountId,
        email: Email,
        hashedPassword: HashedPassword,
        name: Name,
        description: Description
    ): Id
}