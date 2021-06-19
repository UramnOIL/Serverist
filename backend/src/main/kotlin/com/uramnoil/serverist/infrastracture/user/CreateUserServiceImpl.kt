package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.domain.kernel.models.UserId
import com.uramnoil.serverist.domain.user.models.User
import com.uramnoil.serverist.domain.user.repositories.UserRepository
import com.uramnoil.serverist.domain.user.services.CreateUserService
import com.uramnoil.serverist.domain.user.services.CreateUserServiceDto
import java.util.*

class CreateUserServiceImpl(private val repository: UserRepository) : CreateUserService {
    override suspend fun new(dto: CreateUserServiceDto): User {
        val user = dto.run {
            User(
                id = UserId(UUID.randomUUID()),
                accountId = accountId,
                email = email,
                hashedPassword = hashedPassword,
                name = name,
                description = description
            )
        }
        repository.insert(user)

        return user
    }
}