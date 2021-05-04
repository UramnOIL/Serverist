package com.uramnoil.serverist.domain.services.unauthenticateduser

import com.uramnoil.serverist.domain.models.unauthenticateduser.AccountId
import com.uramnoil.serverist.domain.models.unauthenticateduser.Email
import com.uramnoil.serverist.domain.services.user.Password

interface CreateUnauthenticatedUserService {
    suspend fun create(userId: AccountId, email: Email, password: Password)
}