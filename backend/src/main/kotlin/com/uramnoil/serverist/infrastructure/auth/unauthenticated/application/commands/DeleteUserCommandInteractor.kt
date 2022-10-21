package com.uramnoil.serverist.infrastracture.auth.unauthenticated.application.commands

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.auth.unauthenticated.commands.DeleteUserCommandInputPort
import com.uramnoil.serverist.application.auth.unauthenticated.commands.DeleteUserCommandOutputPort
import com.uramnoil.serverist.domain.auth.unauthenticated.models.Id
import com.uramnoil.serverist.domain.auth.unauthenticated.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DeleteUserCommandInteractor(
    private val outputPort: DeleteUserCommandOutputPort,
    private val repository: UserRepository,
    parentContext: CoroutineContext
) :
    DeleteUserCommandInputPort,
    CoroutineScope by CoroutineScope(parentContext + Job(parentContext.job)) {
    override fun execute(id: Uuid) {
        launch {
            val findResult = repository.findById(Id(id)).mapCatching {
                it ?: throw IllegalArgumentException("id=${id}に一致するユーザーが見つかりませんでした。")
            }

            val deleteResult = findResult.mapCatching {
                repository.delete(it).getOrThrow()
            }

            outputPort.handle(deleteResult)
        }
    }
}