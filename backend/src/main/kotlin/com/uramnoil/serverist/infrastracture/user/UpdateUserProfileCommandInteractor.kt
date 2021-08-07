package com.uramnoil.serverist.infrastracture.user

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.user.commands.UpdateUserProfileCommandInputPort
import com.uramnoil.serverist.domain.kernel.models.user.UserId
import com.uramnoil.serverist.domain.user.models.Description
import com.uramnoil.serverist.domain.user.models.Name
import com.uramnoil.serverist.domain.user.repositories.UserRepository

class UpdateUserProfileCommandInteractor(
    private val repository: UserRepository,
) : UpdateUserProfileCommandInputPort {
    override suspend fun execute(id: Uuid, accountId: String, name: String, description: String) =
        repository.findById(UserId(id)).map {
            it ?: return Result.failure<Unit>(IllegalArgumentException("id: ${id}のユーザー見つかりませんでした。"))
            it.apply {
                this.name = Name(name)
                this.description = Description(description)
            }

            repository.update(it)
            Unit
        }
}