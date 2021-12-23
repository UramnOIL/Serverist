package com.uramnoil.serverist.serverist.infrastructure.application.user.command

import com.uramnoil.serverist.domain.common.exception.UserNotFoundByIdException
import com.uramnoil.serverist.domain.common.user.Id
import com.uramnoil.serverist.domain.serverist.repositories.UserRepository
import com.uramnoil.serverist.serverist.application.user.commands.DeleteUserCommandUseCaseInputPort
import com.uramnoil.serverist.serverist.application.user.commands.DeleteUserCommandUseCaseOutputPort
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.coroutines.CoroutineContext

class DeleteUserCommandUseCaseInteractor(
    private val repository: UserRepository,
    private val outputPort: DeleteUserCommandUseCaseOutputPort,
    private val coroutineContext: CoroutineContext
) :
    DeleteUserCommandUseCaseInputPort {
    override fun execute(id: UUID) {
        CoroutineScope(coroutineContext).launch {
            val user = repository.findById(Id(id)).getOrElse {
                outputPort.handle(Result.failure(it))
                return@launch
            }

            user ?: run {
                outputPort.handle(Result.failure(UserNotFoundByIdException(id.toString())))
                return@launch
            }

            outputPort.handle(repository.delete(user))
            return@launch
        }
    }
}