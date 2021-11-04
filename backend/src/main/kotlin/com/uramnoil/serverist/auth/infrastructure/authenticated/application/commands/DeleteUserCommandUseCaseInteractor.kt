package com.uramnoil.serverist.auth.infrastructure.authenticated.application.commands

import com.uramnoil.serverist.auth.application.authenticated.commands.DeleteUserCommandUseCaseInputPort
import com.uramnoil.serverist.domain.auth.authenticated.repositories.UserRepository
import com.uramnoil.serverist.domain.common.exception.UserNotFoundByIdException
import com.uramnoil.serverist.domain.common.user.Id
import java.util.*

class DeleteUserCommandUseCaseInteractor(private val repository: UserRepository) : DeleteUserCommandUseCaseInputPort {
    override suspend fun execute(id: UUID): Result<Unit> {
        val userResult = repository.findById(Id(id))

        val user = userResult.getOrElse {
            return Result.failure(it)
        }

        user ?: return Result.failure(UserNotFoundByIdException(id.toString()))

        return repository.delete(user)
    }
}