package com.uramnoil.serverist.infrastracture.user

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.user.commands.CreateUserCommandInputPort
import com.uramnoil.serverist.application.user.toApplication
import com.uramnoil.serverist.domain.kernel.models.user.AccountId
import com.uramnoil.serverist.domain.kernel.models.user.Email
import com.uramnoil.serverist.domain.kernel.models.user.HashedPassword
import com.uramnoil.serverist.domain.kernel.models.user.UserId
import com.uramnoil.serverist.domain.user.models.Description
import com.uramnoil.serverist.domain.user.models.Name
import com.uramnoil.serverist.domain.user.repositories.UserRepository
import com.uramnoil.serverist.application.user.User as ApplicationUser
import com.uramnoil.serverist.domain.user.models.User as DomainUser

class CreateUserCommandInteractor(
    private val repository: UserRepository
) : CreateUserCommandInputPort {
    override suspend fun execute(
        accountId: String,
        email: String,
        hashedPassword: String,
        name: String,
        description: String
    ): Result<ApplicationUser> {
        val user = DomainUser(
            UserId(Uuid.randomUUID()),
            AccountId(accountId),
            Email(email),
            HashedPassword(hashedPassword),
            Name(name),
            Description(description)
        )

        return repository.insert(user).map {
            user.toApplication()
        }
    }
}