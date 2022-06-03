package com.uramnoil.serverist.serverist.infrastructures.application.usecase.server.commands

import com.uramnoil.serverist.serverist.domain.models.server.Id
import com.uramnoil.serverist.serverist.application.usecases.server.commands.DeleteServerCommandUseCaseInputPort
import com.uramnoil.serverist.serverist.application.usecases.server.commands.DeleteServerCommandUseCaseOutputPort
import com.uramnoil.serverist.serverist.domain.repositories.ServerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.coroutines.CoroutineContext

class DeleteServerCommandUseCaseInteractor(
    private val repository: ServerRepository,
) : DeleteServerCommandUseCaseInputPort {
    override suspend fun execute(id: UUID): Result<Unit> {
        val findByIdResult = repository.findById(Id(id)).mapCatching {
            require(it != null) { "id: ${id}のサーバーが見つかりませんでした。" }
            it
        }
        val deleteResult = findByIdResult.mapCatching {
            repository.delete(it).getOrThrow()
        }
        return deleteResult
    }
}
}
