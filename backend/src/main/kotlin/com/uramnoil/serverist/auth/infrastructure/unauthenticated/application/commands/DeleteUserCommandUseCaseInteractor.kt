package com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.commands

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.auth.application.unauthenticated.commands.DeleteUserCommandUseCaseInputPort
import com.uramnoil.serverist.domain.auth.unauthenticated.models.Id
import com.uramnoil.serverist.domain.auth.unauthenticated.repositories.UserRepository

class DeleteUserCommandUseCaseInteractor(
    private val repository: UserRepository,
) : DeleteUserCommandUseCaseInputPort {
    override suspend fun execute(id: Uuid): Result<Unit> {
        val findResult = repository.findById(Id(id)).mapCatching {
            it ?: throw IllegalArgumentException("id=${id}に一致するユーザーが見つかりませんでした。")
        }

        val deleteResult = findResult.mapCatching {
            repository.delete(it).getOrThrow()
        }

        return deleteResult
    }
}