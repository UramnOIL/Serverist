package com.uramnoil.serverist.domain.user.services

import com.uramnoil.serverist.domain.kernel.models.user.AccountId
import com.uramnoil.serverist.domain.kernel.models.user.Email
import com.uramnoil.serverist.domain.kernel.models.user.HashedPassword
import com.uramnoil.serverist.domain.user.models.Description
import com.uramnoil.serverist.domain.user.models.Name
import com.uramnoil.serverist.domain.user.models.User

interface CreateUserService {
    suspend fun new(
        accountId: AccountId,
        email: Email,
        hashedPassword: HashedPassword,
        name: Name,
        description: Description
    ): User
}