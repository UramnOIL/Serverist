package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.user.commands.UpdateUserProfileCommand
import com.uramnoil.serverist.application.user.commands.UpdateUserProfileCommandDto
import com.uramnoil.serverist.domain.models.user.Description
import com.uramnoil.serverist.domain.models.user.Id
import com.uramnoil.serverist.domain.models.user.Name
import com.uramnoil.serverist.domain.repositories.NotFoundException
import com.uramnoil.serverist.domain.repositories.UserRepository

class UpdateUserProfileCommandImpl(
    private val repository: UserRepository,
) : UpdateUserProfileCommand {
    override suspend fun execute(dto: UpdateUserProfileCommandDto) {
        val user = repository.findById(Id(dto.id))
            ?: throw NotFoundException("UpdateUserCommand#execute: ユーザー(ID: ${dto.id})が見つかりませんでした。")

        user.apply {
            name = Name(dto.name)
            description = Description(dto.description)
        }

        repository.store(user)
    }
}