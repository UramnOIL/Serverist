package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.user.commands.UpdateUserProfileCommand
import com.uramnoil.serverist.domain.kernel.NotFoundException
import com.uramnoil.serverist.domain.kernel.models.user.UserId
import com.uramnoil.serverist.domain.user.models.Description
import com.uramnoil.serverist.domain.user.models.Name
import com.uramnoil.serverist.domain.user.repositories.UserRepository
import java.util.*

class UpdateUserProfileCommandImpl(
    private val repository: UserRepository,
) : UpdateUserProfileCommand {
    override suspend fun execute(id: UUID, accountId: String, name: String, description: String) {
        val user = repository.findById(UserId(id))
            ?: throw NotFoundException("UpdateUserCommand#execute: ユーザー(ID: ${id})が見つかりませんでした。")

        user.apply {
            this.name = Name(name)
            this.description = Description(description)
        }

        repository.update(user)
    }
}