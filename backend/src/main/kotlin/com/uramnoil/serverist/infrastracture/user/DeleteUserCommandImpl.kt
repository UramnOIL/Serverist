package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.user.commands.DeleteUserCommand
import com.uramnoil.serverist.application.user.commands.DeleteUserCommandDto
import com.uramnoil.serverist.domain.models.user.Id
import com.uramnoil.serverist.domain.repositories.NotFoundException
import com.uramnoil.serverist.domain.repositories.UserRepository

class DeleteUserCommandImpl(private val repository: UserRepository) :
    DeleteUserCommand {
    override suspend fun execute(dto: DeleteUserCommandDto) {
        val user = repository.findById(Id(dto.id))
            ?: throw NotFoundException("DeleteUserCommand#execute: ユーザー(ID: ${dto.id})が見つかりませんでした。")

        repository.delete(user)
    }
}