package com.uramnoil.serverist.serverist.infrastructure.application.user.command

import com.uramnoil.serverist.domain.common.user.Id
import com.uramnoil.serverist.domain.user.models.AccountId
import com.uramnoil.serverist.domain.user.models.Description
import com.uramnoil.serverist.domain.user.models.Name
import com.uramnoil.serverist.domain.user.repositories.UserRepository
import com.uramnoil.serverist.serverist.application.user.commands.CreateUserCommandUseCaseInputPort
import com.uramnoil.serverist.serverist.user.infrastructure.application.toApplicationUser
import java.util.*
import com.uramnoil.serverist.domain.user.models.User as DomainUser

class CreateUserCommandUseCaseInteractor(private val repository: UserRepository) : CreateUserCommandUseCaseInputPort {
    override suspend fun execute(
        id: UUID,
        accountId: String,
        name: String,
        description: String
    ): Result<UUID> {
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
        return result.map { it.id }
    }
}