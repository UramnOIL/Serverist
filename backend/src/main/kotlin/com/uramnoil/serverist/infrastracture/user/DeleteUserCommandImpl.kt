package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.user.commands.DeleteUserCommand
import com.uramnoil.serverist.application.user.commands.DeleteUserCommandDto
import com.uramnoil.serverist.domain.kernel.NotFoundException
import com.uramnoil.serverist.domain.user.repositories.UserRepository

class DeleteUserCommandImpl(private val repository: UserRepository) :
    DeleteUserCommand {
    override suspend fun execute(dto: DeleteUserCommandDto) {
        val user = repository.findById(com.uramnoil.serverist.domain.kernel.models.UserId(dto.id))
            ?: throw NotFoundException("DeleteUserCommand#execute: ユーザー(ID: ${dto.id})が見つかりませんでした。")

        repository.delete(user)
    }
}