package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.domain.kernel.models.user.AccountId
import com.uramnoil.serverist.domain.kernel.models.user.Email
import com.uramnoil.serverist.domain.kernel.models.user.HashedPassword
import com.uramnoil.serverist.domain.kernel.models.user.UserId
import com.uramnoil.serverist.domain.user.models.Description
import com.uramnoil.serverist.domain.user.models.Name
import com.uramnoil.serverist.domain.user.models.User
import com.uramnoil.serverist.domain.user.repositories.UserRepository
import com.uramnoil.serverist.domain.user.services.CreateUserService
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