package com.uramnoil.serverist.infrastracture.unauthenticateduser

import com.uramnoil.serverist.domain.kernel.models.HashedPassword
import com.uramnoil.serverist.domain.unauthenticateduser.models.AccountId
import com.uramnoil.serverist.domain.unauthenticateduser.models.Email
import com.uramnoil.serverist.domain.unauthenticateduser.models.Id
import com.uramnoil.serverist.domain.unauthenticateduser.models.UnauthenticatedUser
import com.uramnoil.serverist.domain.unauthenticateduser.repositories.UnauthenticatedUserRepository
import com.uramnoil.serverist.domain.unauthenticateduser.services.CreateUnauthenticatedUserService
import java.util.*

class CreateUnauthenticatedUserServiceImpl(private val repository: UnauthenticatedUserRepository) :
    CreateUnauthenticatedUserService {
    override suspend fun execute(
        accountId: AccountId,
        email: Email,
        hashedPassword: HashedPassword
    ): UnauthenticatedUser {
        val user = UnauthenticatedUser(
            id = Id(UUID.randomUUID()),
            accountId = accountId,
            email = email,
            hashedPassword = hashedPassword,
        )

        repository.insert(user)

        return user
    }
}