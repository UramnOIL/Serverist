package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.application.server.commands.DeleteServerCommand
import com.uramnoil.serverist.application.server.commands.DeleteServerCommandDto
import com.uramnoil.serverist.domain.models.server.models.Id
import com.uramnoil.serverist.domain.models.server.repositories.ServerRepository
import com.uramnoil.serverist.domain.repositories.NotFoundException

class DeleteServerCommandImpl(
    private val repository: ServerRepository,
) : DeleteServerCommand {
    override suspend fun execute(dto: DeleteServerCommandDto) {
        val server = repository.findById(Id(dto.id))
            ?: throw NotFoundException("DeleteServerCommand#Execute: サーバー(Id: ${dto.id})が見つかりませんでした。")
        repository.delete(server)
    }
}