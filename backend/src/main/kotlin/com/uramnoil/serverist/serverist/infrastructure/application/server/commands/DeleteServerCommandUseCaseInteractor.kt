package com.uramnoil.serverist.serverist.infrastructure.application.server.commands

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.domain.server.repositories.ServerRepository
import com.uramnoil.serverist.serverist.application.server.commands.DeleteServerCommandUseCaseInputPort

class DeleteServerCommandUseCaseInteractor(
    private val repository: ServerRepository,
) : DeleteServerCommandUseCaseInputPort {
    override suspend fun execute(id: Uuid): Result<Unit> {
        val findByIdResult = repository.findById(com.uramnoil.serverist.domain.server.models.Id(id)).mapCatching {
            it ?: throw IllegalArgumentException("id: ${id}のサーバーが見つかりませんでした。")
        }
        val deleteResult = findByIdResult.mapCatching {
            repository.delete(it).getOrThrow()
        }
        return deleteResult
    }
}