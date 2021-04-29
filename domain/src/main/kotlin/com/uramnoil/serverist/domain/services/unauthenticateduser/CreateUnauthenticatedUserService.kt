package com.uramnoil.serverist.domain.services.unauthenticateduser

import com.uramnoil.serverist.domain.models.kernel.user.Password
import com.uramnoil.serverist.domain.models.unauthenticateduser.AccountId
import com.uramnoil.serverist.domain.models.unauthenticateduser.Email

interface CreateUnauthenticatedUserService {
    suspend fun create(userId: AccountId, email: Email, password: Password)
}