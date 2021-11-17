package com.uramnoil.serverist.auth.infrastructure.authenticated.application.commands

import com.uramnoil.serverist.application.authenticated.commands.DeleteUserCommandUseCaseInputPort
import com.uramnoil.serverist.application.authenticated.commands.DeleteUserCommandUseCaseOutputPort
import com.uramnoil.serverist.domain.auth.authenticated.repositories.UserRepository
import com.uramnoil.serverist.domain.common.exception.UserNotFoundByIdException
import com.uramnoil.serverist.domain.common.user.Id
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext

class DeleteUserCommandUseCaseInteractor(
    private val repository: UserRepository,
    private val outputPort: DeleteUserCommandUseCaseOutputPort,
    private val coroutineContext: CoroutineContext,
) : DeleteUserCommandUseCaseInputPort {
    override fun execute(id: UUID) {
        CoroutineScope(coroutineContext).launch {
            val userResult = repository.findById(Id(id))

            val user = userResult.getOrElse {
                outputPort.handle(Result.failure(it))
                return@launch
            }

            user ?: kotlin.run {
                outputPort.handle(Result.failure(UserNotFoundByIdException(id.toString())))
                return@launch
            }

            outputPort.handle(repository.delete(user))
        }
    }
}