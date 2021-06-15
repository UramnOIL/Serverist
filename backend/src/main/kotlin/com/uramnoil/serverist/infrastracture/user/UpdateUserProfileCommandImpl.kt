package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.user.commands.UpdateUserProfileCommand
import com.uramnoil.serverist.application.user.commands.UpdateUserProfileCommandDto
import com.uramnoil.serverist.domain.kernel.NotFoundException
import com.uramnoil.serverist.domain.user.models.Description
import com.uramnoil.serverist.domain.user.models.Name
import com.uramnoil.serverist.domain.user.repositories.UserRepository

class UpdateUserProfileCommandImpl(
    private val repository: UserRepository,
) : UpdateUserProfileCommand {
    override suspend fun execute(dto: UpdateUserProfileCommandDto) {
        val user = repository.findById(com.uramnoil.serverist.domain.kernel.models.UserId(dto.id))
            ?: throw NotFoundException("UpdateUserCommand#execute: ユーザー(ID: ${dto.id})が見つかりませんでした。")

        user.apply {
            name = Name(dto.name)
            description = Description(dto.description)
        }

        repository.update(user)
    }
}