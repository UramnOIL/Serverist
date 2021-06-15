package com.uramnoil.serverist.domain.models.unauthenticateduser.services

import com.uramnoil.serverist.domain.models.kernel.models.Password
import com.uramnoil.serverist.domain.models.unauthenticateduser.models.AccountId
import com.uramnoil.serverist.domain.models.unauthenticateduser.models.Email

interface CreateUnauthenticatedUserService {
    suspend fun create(userId: AccountId, email: Email, password: Password)
}