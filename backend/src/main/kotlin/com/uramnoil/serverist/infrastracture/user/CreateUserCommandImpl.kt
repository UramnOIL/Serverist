package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.user.commands.CreateUserCommand
import com.uramnoil.serverist.application.user.toApplication
import com.uramnoil.serverist.domain.kernel.models.user.AccountId
import com.uramnoil.serverist.domain.kernel.models.user.Email
import com.uramnoil.serverist.domain.kernel.models.user.HashedPassword
import com.uramnoil.serverist.domain.kernel.models.user.UserId
import com.uramnoil.serverist.domain.user.models.Description
import com.uramnoil.serverist.domain.user.models.Name
import com.uramnoil.serverist.domain.user.repositories.UserRepository
import java.util.*
import com.uramnoil.serverist.application.user.User as ApplicationUser
import com.uramnoil.serverist.domain.user.models.User as DomainUser

class CreateUserCommandImpl(
    private val repository: UserRepository
) : CreateUserCommand {
    override suspend fun execute(
        accountId: String,
        email: String,
        hashedPassword: String,
        name: String,
        description: String
    ): ApplicationUser {
        val user = DomainUser(
            UserId(UUID.randomUUID()),
            AccountId(accountId),
            Email(email),
            HashedPassword(hashedPassword),
            Name(name),
            Description(description)
        )

        repository.insert(user)

        return user.toApplication()
    }
}