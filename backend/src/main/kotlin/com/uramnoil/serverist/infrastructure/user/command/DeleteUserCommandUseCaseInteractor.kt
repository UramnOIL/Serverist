package com.uramnoil.serverist.infrastructure.user.command

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.user.commands.DeleteUserCommandUseCaseInputPort
import com.uramnoil.serverist.domain.common.exception.NotFoundException
import com.uramnoil.serverist.domain.common.user.Id
import com.uramnoil.serverist.domain.user.repositories.UserRepository

class DeleteUserCommandUseCaseInteractor(private val repository: UserRepository) : DeleteUserCommandUseCaseInputPort {
    override suspend fun execute(id: Uuid): Result<Unit> {
        val user = repository.findById(Id(id)).getOrElse {
            return Result.failure(it)
        }

        user ?: run {
            return Result.failure(NotFoundException("id: ${id}に一致するユーザーが見つかりませんでした。"))
        }

        return repository.delete(user)
    }
}