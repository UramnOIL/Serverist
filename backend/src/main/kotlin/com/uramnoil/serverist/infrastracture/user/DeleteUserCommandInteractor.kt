package com.uramnoil.serverist.infrastracture.user

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.user.commands.DeleteUserCommandInputPort
import com.uramnoil.serverist.domain.kernel.models.user.UserId
import com.uramnoil.serverist.domain.user.repositories.UserRepository

class DeleteUserCommandInteractor(private val repository: UserRepository) :
    DeleteUserCommandInputPort {
    override suspend fun execute(id: Uuid): Result<Unit> {
        val user = repository.findById(UserId(id)).getOrElse {
            return Result.failure(it)
        }

        user ?: return Result.failure(IllegalArgumentException("id: ${id}に一致するユーザーが見つかりませんでした。"))

        return repository.delete(user)
    }
}