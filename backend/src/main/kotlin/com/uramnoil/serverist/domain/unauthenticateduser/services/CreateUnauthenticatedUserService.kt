package com.uramnoil.serverist.domain.unauthenticateduser.services

import com.uramnoil.serverist.domain.kernel.models.Password
import com.uramnoil.serverist.domain.unauthenticateduser.models.AccountId
import com.uramnoil.serverist.domain.unauthenticateduser.models.Email

interface CreateUnauthenticatedUserService {
    suspend fun create(userId: AccountId, email: Email, password: Password)
}