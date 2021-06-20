package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.user.commands.DeleteUserCommand
import com.uramnoil.serverist.domain.kernel.NotFoundException
import com.uramnoil.serverist.domain.kernel.models.user.UserId
import com.uramnoil.serverist.domain.user.repositories.UserRepository
import java.util.*

class DeleteUserCommandImpl(private val repository: UserRepository) :
    DeleteUserCommand {
    override suspend fun execute(id: UUID) {
        val user = repository.findById(UserId(id))
            ?: throw NotFoundException("DeleteUserCommand#execute: ユーザー(ID: ${id})が見つかりませんでした。")

        repository.delete(user)
    }
}