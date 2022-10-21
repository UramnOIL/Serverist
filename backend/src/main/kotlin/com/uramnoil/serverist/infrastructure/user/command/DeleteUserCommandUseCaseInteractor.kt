package com.uramnoil.serverist.infrastructure.user.command

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.user.commands.DeleteUserCommandUseCaseInputPort
import com.uramnoil.serverist.application.user.commands.DeleteUserCommandUseCaseOutputPort
import com.uramnoil.serverist.domain.common.exception.NotFoundException
import com.uramnoil.serverist.domain.common.user.UserId
import com.uramnoil.serverist.domain.user.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DeleteUserCommandUseCaseInteractor(
    private val outputPort: DeleteUserCommandUseCaseOutputPort,
    private val repository: UserRepository,
    parentContext: CoroutineContext
) :
    DeleteUserCommandUseCaseInputPort,
    CoroutineScope by CoroutineScope(parentContext + Job(parentContext.job)) {
    override fun execute(id: Uuid) {
        launch {
            val user = repository.findById(UserId(id)).getOrElse {
                outputPort.handle(Result.failure(it))
                return@launch
            }

            user ?: run {
                outputPort.handle(Result.failure(NotFoundException("id: ${id}に一致するユーザーが見つかりませんでした。")))
                return@launch
            }

            val deleteResult = repository.delete(user)
            outputPort.handle(deleteResult)
        }
    }
}