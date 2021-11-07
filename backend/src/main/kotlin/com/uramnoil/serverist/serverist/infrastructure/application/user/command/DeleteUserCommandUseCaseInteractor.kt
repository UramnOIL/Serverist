package com.uramnoil.serverist.serverist.infrastructure.application.user.command

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.domain.common.exception.UserNotFoundByIdException
import com.uramnoil.serverist.domain.common.user.Id
import com.uramnoil.serverist.domain.user.repositories.UserRepository
import com.uramnoil.serverist.serverist.application.user.commands.DeleteUserCommandUseCaseInputPort

class DeleteUserCommandUseCaseInteractor(private val repository: UserRepository) : DeleteUserCommandUseCaseInputPort {
    override suspend fun execute(id: Uuid): Result<Unit> {
        val user = repository.findById(Id(id)).getOrElse {
            return Result.failure(it)
        }

        user ?: run {
            return Result.failure(UserNotFoundByIdException(id.toString()))
        }

        return repository.delete(user)
    }
}