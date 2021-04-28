package com.uramnoil.serverist.domain.services.user

import com.uramnoil.serverist.domain.models.user.AccountId
import com.uramnoil.serverist.domain.models.user.Description
import com.uramnoil.serverist.domain.models.user.Id
import com.uramnoil.serverist.domain.models.user.Name

interface CreateUserService {
    suspend fun new(
        accountId: AccountId,
        email: Email,
        password: HashedPassword,
        name: Name,
        description: Description
    ): Id
}