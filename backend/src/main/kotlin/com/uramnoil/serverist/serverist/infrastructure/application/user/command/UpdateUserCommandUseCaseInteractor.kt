package com.uramnoil.serverist.serverist.infrastructure.application.user.command

import com.uramnoil.serverist.domain.common.exception.UserNotFoundByIdException
import com.uramnoil.serverist.domain.common.user.Id
import com.uramnoil.serverist.domain.serverist.models.user.AccountId
import com.uramnoil.serverist.domain.serverist.models.user.Description
import com.uramnoil.serverist.domain.serverist.models.user.Name
import com.uramnoil.serverist.domain.serverist.repositories.UserRepository
import com.uramnoil.serverist.serverist.application.user.commands.UpdateUserCommandUseCaseInputPort
import java.util.*

class UpdateUserCommandUseCaseInteractor(private val repository: UserRepository) : UpdateUserCommandUseCaseInputPort {
    override suspend fun execute(id: UUID, accountId: String, name: String, description: String): Result<Unit> {
        val user = repository.findById(Id(id)).getOrElse {
            return Result.failure(it)
        }

        user ?: run {
            return Result.failure(UserNotFoundByIdException(id.toString()))
        }

        user.apply {
            this.accountId = AccountId(accountId)
            this.name = Name(name)
            this.description = Description(description)
        }

        return repository.update(user)
    }
}