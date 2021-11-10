package com.uramnoil.serverist.serverist.infrastructure.application.server.commands

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.domain.serverist.models.server.Id
import com.uramnoil.serverist.domain.serverist.repositories.ServerRepository
import com.uramnoil.serverist.serverist.application.server.commands.DeleteServerCommandUseCaseInputPort

class DeleteServerCommandUseCaseInteractor(
    private val repository: ServerRepository,
) : DeleteServerCommandUseCaseInputPort {
    override suspend fun execute(id: Uuid): Result<Unit> {
        val findByIdResult = repository.findById(Id(id)).mapCatching {
            it ?: throw IllegalArgumentException("id: ${id}のサーバーが見つかりませんでした。")
        }
        val deleteResult = findByIdResult.mapCatching {
            repository.delete(it).getOrThrow()
        }
        return deleteResult
    }
}