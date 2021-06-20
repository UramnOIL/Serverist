package com.uramnoil.serverist.domain.unauthenticateduser.services

import com.uramnoil.serverist.domain.kernel.models.HashedPassword
import com.uramnoil.serverist.domain.unauthenticateduser.models.AccountId
import com.uramnoil.serverist.domain.unauthenticateduser.models.Email
import com.uramnoil.serverist.domain.unauthenticateduser.models.UnauthenticatedUser

interface CreateUnauthenticatedUserService {
    suspend fun execute(
        accountId: AccountId,
        email: Email,
        hashedPassword: HashedPassword
    ): UnauthenticatedUser
}