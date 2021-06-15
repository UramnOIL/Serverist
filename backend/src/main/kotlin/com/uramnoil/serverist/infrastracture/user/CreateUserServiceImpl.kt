package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.domain.models.kernel.UserId
import com.uramnoil.serverist.domain.models.user.*
import com.uramnoil.serverist.domain.repositories.UserRepository
import com.uramnoil.serverist.domain.services.user.CreateUserService
import java.util.*

class CreateUserServiceImpl(private val repository: UserRepository) : CreateUserService {
    override suspend fun new(
        accountId: AccountId,
        email: Email,
        hashedPassword: HashedPassword,
        name: Name,
        description: Description
    ): User {
        val user = User(
            id = UserId(UUID.randomUUID()),
            accountId = accountId,
            email = email,
            hashedPassword = hashedPassword,
            name = name,
            description = description
        )
        repository.insert(user)

        return user
    }
}