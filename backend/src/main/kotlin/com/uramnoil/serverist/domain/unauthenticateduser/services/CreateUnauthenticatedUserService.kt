package com.uramnoil.serverist.domain.unauthenticateduser.services

import com.uramnoil.serverist.domain.kernel.models.Password
import com.uramnoil.serverist.domain.unauthenticateduser.models.AccountId
import com.uramnoil.serverist.domain.unauthenticateduser.models.Email
import com.uramnoil.serverist.domain.unauthenticateduser.models.UnauthenticatedUser

data class CreateUnauthenticatedUserServiceDto(val accountId: AccountId, val email: Email, val password: Password)

interface CreateUnauthenticatedUserService {
    suspend fun execute(dto: CreateUnauthenticatedUserServiceDto): UnauthenticatedUser
}