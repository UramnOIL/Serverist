package com.uramnoil.serverist.infrastracture.unauthenticateduser

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.unauthenticateduser.commands.CreateUnauthenticatedUserCommand
import com.uramnoil.serverist.domain.kernel.models.user.AccountId
import com.uramnoil.serverist.domain.kernel.models.user.Email
import com.uramnoil.serverist.domain.kernel.models.user.Password
import com.uramnoil.serverist.domain.kernel.services.HashPasswordService
import com.uramnoil.serverist.domain.unauthenticateduser.models.Id
import com.uramnoil.serverist.domain.unauthenticateduser.repositories.UnauthenticatedUserRepository
import com.uramnoil.serverist.application.unauthenticateduser.UnauthenticatedUser as ApplicationUnauthenticatedUser
import com.uramnoil.serverist.domain.unauthenticateduser.models.UnauthenticatedUser as DomainUnauthenticatedUser

class CreateUnauthenticatedUserCommandImpl(
    private val repository: UnauthenticatedUserRepository,
    private val service: HashPasswordService
) : CreateUnauthenticatedUserCommand {
    override suspend fun execute(accountId: String, email: String, password: String): ApplicationUnauthenticatedUser {
        val hashedPassword = service.hash(Password(password))
        val user = DomainUnauthenticatedUser(
            id = Id(Uuid.randomUUID()),
            accountId = AccountId(accountId),
            email = Email(email),
            hashedPassword = hashedPassword,
        )

        repository.insert(user)

        return user.toApplication()
    }
}