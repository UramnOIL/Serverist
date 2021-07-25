package com.uramnoil.serverist.infrastracture.unauthenticateduser

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.unauthenticateduser.commands.DeleteUnauthenticatedUserCommand
import com.uramnoil.serverist.domain.unauthenticateduser.models.Id
import com.uramnoil.serverist.domain.unauthenticateduser.repositories.UnauthenticatedUserRepository

class DeleteUnauthenticatedUserCommandImpl(private val repository: UnauthenticatedUserRepository) :
    DeleteUnauthenticatedUserCommand {
    override suspend fun execute(id: Uuid): Result<Unit> {
        val user = repository.findById(Id(id)).getOrElse {
            return Result.failure(it)
        }

        user ?: throw IllegalArgumentException("id=${id}に一致するユーザーが見つかりませんでした。")

        return repository.delete(user)
    }
}