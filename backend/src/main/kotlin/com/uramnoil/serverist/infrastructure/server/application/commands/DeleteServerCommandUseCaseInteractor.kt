package com.uramnoil.serverist.infrastracture.server.application.commands

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.server.commands.DeleteServerCommandUseCaseInputPort
import com.uramnoil.serverist.application.server.commands.DeleteServerCommandUseCaseOutputPort
import com.uramnoil.serverist.domain.server.repositories.ServerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DeleteServerCommandUseCaseInteractor(
    private val outputPort: DeleteServerCommandUseCaseOutputPort,
    private val repository: ServerRepository,
    parentContext: CoroutineContext
) : DeleteServerCommandUseCaseInputPort, CoroutineScope by CoroutineScope(parentContext + Job(parentContext.job)) {
    override fun execute(id: Uuid) {
        launch {
            val findByIdResult = repository.findById(com.uramnoil.serverist.domain.server.models.Id(id)).mapCatching {
                it ?: throw IllegalArgumentException("id: ${id}のサーバーが見つかりませんでした。")
            }
            val deleteResult = findByIdResult.mapCatching {
                repository.delete(it).getOrThrow()
            }
            outputPort.handle(deleteResult)
        }
    }
}