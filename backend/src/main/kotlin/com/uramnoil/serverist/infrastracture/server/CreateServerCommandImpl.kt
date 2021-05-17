package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.application.server.commands.CreateServerCommand
import com.uramnoil.serverist.application.server.commands.CreateServerDto
import com.uramnoil.serverist.domain.models.server.Address
import com.uramnoil.serverist.domain.models.server.Description
import com.uramnoil.serverist.domain.models.server.Name
import com.uramnoil.serverist.domain.models.server.Port
import com.uramnoil.serverist.domain.models.user.Id
import com.uramnoil.serverist.domain.repositories.NotFoundException
import com.uramnoil.serverist.domain.repositories.UserRepository
import com.uramnoil.serverist.domain.services.server.CreateServerService

class CreateServerCommandImpl(
    private val repository: UserRepository,
    private val service: CreateServerService,
) : CreateServerCommand {
    override suspend fun execute(dto: CreateServerDto) {
        val user = repository.findById(Id(dto.ownerId))
            ?: throw NotFoundException("CreateServerCommand#execute: ユーザー(Id: ${dto.ownerId})が見つかりませんでした。")

        dto.run {
            service.new(
                Name(name),
                user,
                Address(address),
                Port(port),
                Description(description)
            )
        }
    }
}