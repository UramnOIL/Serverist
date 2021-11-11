package com.uramnoil.serverist.serverist.infrastructure.application.user.command

import com.uramnoil.serverist.domain.common.exception.UserNotFoundByIdException
import com.uramnoil.serverist.domain.common.user.Id
import com.uramnoil.serverist.domain.serverist.repositories.UserRepository
import com.uramnoil.serverist.serverist.application.user.commands.DeleteUserCommandUseCaseInputPort
import java.util.*

class DeleteUserCommandUseCaseInteractor(private val repository: UserRepository) : DeleteUserCommandUseCaseInputPort {
    override suspend fun execute(id: UUID): Result<Unit> {
        val user = repository.findById(Id(id)).getOrElse {
            return Result.failure(it)
        }

        user ?: run {
            return Result.failure(UserNotFoundByIdException(id.toString()))
        }

        return repository.delete(user)
    }
}