package com.uramnoil.serverist.serverist.infrastructure.application.user.command

import com.uramnoil.serverist.domain.common.user.Id
import com.uramnoil.serverist.domain.serverist.models.user.AccountId
import com.uramnoil.serverist.domain.serverist.models.user.Description
import com.uramnoil.serverist.domain.serverist.models.user.Name
import com.uramnoil.serverist.domain.serverist.repositories.UserRepository
import com.uramnoil.serverist.serverist.application.user.commands.CreateUserCommandUseCaseInputPort
import com.uramnoil.serverist.serverist.user.infrastructure.application.toApplicationUser
import java.util.*

class CreateUserCommandUseCaseInteractor(private val repository: UserRepository) : CreateUserCommandUseCaseInputPort {
    override suspend fun execute(
        id: UUID,
        accountId: String,
        name: String,
        description: String
    ): Result<UUID> {
        val userResult = com.uramnoil.serverist.domain.serverist.models.user.User.new(
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