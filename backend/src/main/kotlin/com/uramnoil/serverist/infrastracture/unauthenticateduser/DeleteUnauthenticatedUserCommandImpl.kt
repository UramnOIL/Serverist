package com.uramnoil.serverist.infrastracture.unauthenticateduser

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.unauthenticateduser.commands.DeleteUnauthenticatedUserCommandInputPort
import com.uramnoil.serverist.domain.unauthenticateduser.models.Id
import com.uramnoil.serverist.domain.unauthenticateduser.repositories.UnauthenticatedUserRepository

class DeleteUnauthenticatedUserCommandInteractor(private val repository: UnauthenticatedUserRepository) :
    DeleteUnauthenticatedUserCommandInputPort {
    override suspend fun execute(id: Uuid): Result<Unit> {
        val user = repository.findById(Id(id)).getOrElse {
            return Result.failure(it)
        }

        user ?: throw IllegalArgumentException("id=${id}に一致するユーザーが見つかりませんでした。")

        return repository.delete(user)
    }
}