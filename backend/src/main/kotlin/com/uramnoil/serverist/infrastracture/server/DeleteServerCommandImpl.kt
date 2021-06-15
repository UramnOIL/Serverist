package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.application.server.commands.DeleteServerCommand
import com.uramnoil.serverist.application.server.commands.DeleteServerCommandDto
import com.uramnoil.serverist.domain.kernel.NotFoundException
import com.uramnoil.serverist.domain.server.repositories.ServerRepository

class DeleteServerCommandImpl(
    private val repository: ServerRepository,
) : DeleteServerCommand {
    override suspend fun execute(dto: DeleteServerCommandDto) {
        val server = repository.findById(com.uramnoil.serverist.domain.server.models.Id(dto.id))
            ?: throw NotFoundException("DeleteServerCommand#Execute: サーバー(Id: ${dto.id})が見つかりませんでした。")
        repository.delete(server)
    }
}