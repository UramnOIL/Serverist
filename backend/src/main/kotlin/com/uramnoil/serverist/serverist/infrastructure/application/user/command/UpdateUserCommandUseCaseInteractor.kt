package com.uramnoil.serverist.serverist.infrastructure.application.user.command

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.domain.common.exception.UserNotFoundByIdException
import com.uramnoil.serverist.domain.common.user.Id
import com.uramnoil.serverist.domain.serverist.models.user.Description
import com.uramnoil.serverist.domain.serverist.models.user.Name
import com.uramnoil.serverist.domain.serverist.repositories.UserRepository
import com.uramnoil.serverist.serverist.application.user.commands.UpdateUserCommandUseCaseInputPort

class UpdateUserCommandUseCaseInteractor(private val repository: UserRepository) : UpdateUserCommandUseCaseInputPort {
    override suspend fun execute(id: Uuid, accountId: String, name: String, description: String): Result<Unit> {
        val user = repository.findById(Id(id)).getOrElse {
            return Result.failure(it)
        }

        user ?: run {
            return Result.failure(UserNotFoundByIdException(id.toString()))
        }

        user.apply {
            this.name = Name(name)
            this.description = Description(description)
        }

        return repository.update(user)
    }
}