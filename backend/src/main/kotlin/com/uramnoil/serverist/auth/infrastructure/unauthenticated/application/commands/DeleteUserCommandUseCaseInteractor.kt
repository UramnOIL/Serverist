package com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.commands

import com.uramnoil.serverist.auth.application.unauthenticated.commands.DeleteUserCommandUseCaseInputPort
import com.uramnoil.serverist.domain.auth.unauthenticated.models.Id
import com.uramnoil.serverist.domain.auth.unauthenticated.repositories.UserRepository
import java.util.*

class DeleteUserCommandUseCaseInteractor(
    private val repository: UserRepository,
) : DeleteUserCommandUseCaseInputPort {
    override fun execute(id: UUID): Result<Unit> {
        val findResult = repository.findById(Id(id)).mapCatching {
            it ?: throw IllegalArgumentException("id=${id}に一致するユーザーが見つかりませんでした。")
        }

        val deleteResult = findResult.mapCatching {
            repository.delete(it).getOrThrow()
        }

        return deleteResult
    }
}