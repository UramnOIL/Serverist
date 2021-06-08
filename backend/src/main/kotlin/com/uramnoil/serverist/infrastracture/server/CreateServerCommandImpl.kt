package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.application.server.commands.CreateServerCommand
import com.uramnoil.serverist.application.server.commands.CreateServerCommandDto
import com.uramnoil.serverist.domain.models.server.Address
import com.uramnoil.serverist.domain.models.server.Description
import com.uramnoil.serverist.domain.models.server.Name
import com.uramnoil.serverist.domain.models.server.Port
import com.uramnoil.serverist.domain.models.user.Id
import com.uramnoil.serverist.domain.repositories.NotFoundException
import com.uramnoil.serverist.domain.repositories.UserRepository
import com.uramnoil.serverist.domain.services.server.CreateServerService
import java.util.*

class CreateServerCommandImpl(
    private val repository: UserRepository,
    private val service: CreateServerService,
) : CreateServerCommand {
    override suspend fun execute(dto: CreateServerCommandDto): UUID {
        val user = repository.findById(Id(dto.ownerId))
            ?: throw NotFoundException("CreateServerCommand#execute: ユーザー(Id: ${dto.ownerId})が見つかりませんでした。")

        val id = dto.run {
            service.new(
                Name(name),
                user,
                Address(address),
                Port(port),
                Description(description)
            )
        }

        return id.value
    }
}