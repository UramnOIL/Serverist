package com.uramnoil.serverist.user.infrastructure.application.command

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.domain.common.user.Id
import com.uramnoil.serverist.domain.user.models.AccountId
import com.uramnoil.serverist.domain.user.models.Description
import com.uramnoil.serverist.domain.user.models.Name
import com.uramnoil.serverist.domain.user.repositories.UserRepository
import com.uramnoil.serverist.user.application.User
import com.uramnoil.serverist.user.application.commands.CreateUserCommandUseCaseInputPort
import com.uramnoil.serverist.user.infrastructure.application.toApplicationUser
import com.uramnoil.serverist.domain.user.models.User as DomainUser

class CreateUserCommandUseCaseInteractor(private val repository: UserRepository) : CreateUserCommandUseCaseInputPort {
    override suspend fun execute(
        id: Uuid,
        accountId: String,
        name: String,
        description: String
    ): Result<User> {
        val userResult = DomainUser.new(
            Id(id),
            AccountId(accountId),
            Name(name),
            Description(description)
        )
        val result = userResult.mapCatching {
            repository.insert(it).getOrThrow()
            it.toApplicationUser()
        }
        return result
    }
}