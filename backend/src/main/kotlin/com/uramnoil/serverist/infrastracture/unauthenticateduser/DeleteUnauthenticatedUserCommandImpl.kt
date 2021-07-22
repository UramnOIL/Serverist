package com.uramnoil.serverist.infrastracture.unauthenticateduser

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.unauthenticateduser.commands.DeleteUnauthenticatedUserCommand
import com.uramnoil.serverist.domain.unauthenticateduser.models.Id
import com.uramnoil.serverist.domain.unauthenticateduser.models.UnauthenticatedUser
import com.uramnoil.serverist.domain.unauthenticateduser.repositories.UnauthenticatedUserRepository

class DeleteUnauthenticatedUserCommandImpl(private val repository: UnauthenticatedUserRepository) :
    DeleteUnauthenticatedUserCommand {
    override suspend fun execute(id: Uuid) {
        val user = repository.findById(Id(id))

        if (user !is UnauthenticatedUser) {
            throw IllegalArgumentException("id=${id}と一致するユーザーが見つかりませんでした。")
        }

        repository.delete(user)
    }
}