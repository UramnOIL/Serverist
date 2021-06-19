package com.uramnoil.serverist.infrastracture.unauthenticated

import com.uramnoil.serverist.domain.unauthenticateduser.models.Id
import com.uramnoil.serverist.domain.unauthenticateduser.models.UnauthenticatedUser
import com.uramnoil.serverist.domain.unauthenticateduser.repositories.UnauthenticatedUserRepository
import com.uramnoil.serverist.domain.unauthenticateduser.services.CreateUnauthenticatedUserService
import com.uramnoil.serverist.domain.unauthenticateduser.services.CreateUnauthenticatedUserServiceDto
import java.util.*

class CreateUnauthenticatedUserServiceImpl(private val repository: UnauthenticatedUserRepository) :
    CreateUnauthenticatedUserService {
    override suspend fun execute(dto: CreateUnauthenticatedUserServiceDto): UnauthenticatedUser {
        val user = dto.run {
            UnauthenticatedUser(
                id = Id(UUID.randomUUID()),
                accountId = accountId,
                email = email,
                hashedPassword = hashedPassword,
            )
        }

        repository.insert(user)

        return user
    }
}