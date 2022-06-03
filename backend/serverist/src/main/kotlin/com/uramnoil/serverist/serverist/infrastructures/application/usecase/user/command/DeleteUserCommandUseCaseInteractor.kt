package com.uramnoil.serverist.serverist.infrastructures.application.usecase.user.command

import com.uramnoil.serverist.common.domain.exception.UserNotFoundByIdException
import com.uramnoil.serverist.common.domain.models.kernel.UserId
import com.uramnoil.serverist.serverist.application.usecases.user.commands.DeleteUserCommandUseCaseInputPort
import com.uramnoil.serverist.serverist.domain.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.coroutines.CoroutineContext

class DeleteUserCommandUseCaseInteractor(
    private val repository: UserRepository,
) :
    DeleteUserCommandUseCaseInputPort {
    override suspend fun execute(id: UUID): Result<Unit> {
        val user = repository.findById(UserId(id)).getOrElse {
            return Result.failure(it)
        }

        user ?: run {
            return Result.failure(UserNotFoundByIdException(id.toString()))
        }

        return repository.delete(user)
    }
}

