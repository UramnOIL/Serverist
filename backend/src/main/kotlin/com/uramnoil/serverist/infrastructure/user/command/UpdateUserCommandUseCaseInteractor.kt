package com.uramnoil.serverist.infrastructure.user.command

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.user.commands.UpdateUserCommandUseCaseInputPort
import com.uramnoil.serverist.domain.common.exception.NotFoundException
import com.uramnoil.serverist.domain.common.user.Id
import com.uramnoil.serverist.domain.user.models.Description
import com.uramnoil.serverist.domain.user.models.Name
import com.uramnoil.serverist.domain.user.repositories.UserRepository

class UpdateUserCommandUseCaseInteractor(private val repository: UserRepository) : UpdateUserCommandUseCaseInputPort {
    override suspend fun execute(id: Uuid, accountId: String, name: String, description: String): Result<Unit> {
        val user = repository.findById(Id(id)).getOrElse {
            return Result.failure(it)
        }

        user ?: run {
            return Result.failure(NotFoundException("id: ${id}のユーザー見つかりませんでした。"))
        }

        user.apply {
            this.name = Name(name)
            this.description = Description(description)
        }

        return repository.update(user)
    }
}