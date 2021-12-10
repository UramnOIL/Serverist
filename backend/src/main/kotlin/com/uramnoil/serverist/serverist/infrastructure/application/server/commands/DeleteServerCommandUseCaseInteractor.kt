package com.uramnoil.serverist.serverist.infrastructure.application.server.commands

import com.uramnoil.serverist.domain.serverist.models.server.Id
import com.uramnoil.serverist.domain.serverist.repositories.ServerRepository
import com.uramnoil.serverist.serverist.application.server.commands.DeleteServerCommandUseCaseInputPort
import com.uramnoil.serverist.serverist.application.server.commands.DeleteServerCommandUseCaseOutputPort
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext

class DeleteServerCommandUseCaseInteractor(
    private val repository: ServerRepository,
    private val outputPort: DeleteServerCommandUseCaseOutputPort,
    private val coroutineContext: CoroutineContext
) : DeleteServerCommandUseCaseInputPort {
    override fun execute(id: UUID) {
        CoroutineScope(coroutineContext).launch {
            val findByIdResult = repository.findById(Id(id)).mapCatching {
                it ?: throw IllegalArgumentException("id: ${id}のサーバーが見つかりませんでした。")
            }
            val deleteResult = findByIdResult.mapCatching {
                repository.delete(it).getOrThrow()
            }
            outputPort.handle(deleteResult)
            return@launch
        }
    }
}