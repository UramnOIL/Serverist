package com.uramnoil.serverist.infrastracture.server

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.server.commands.DeleteServerCommand
import com.uramnoil.serverist.domain.kernel.NotFoundException
import com.uramnoil.serverist.domain.server.repositories.ServerRepository

class DeleteServerCommandImpl(
    private val repository: ServerRepository,
) : DeleteServerCommand {
    override suspend fun execute(id: Uuid) {
        val server = repository.findById(com.uramnoil.serverist.domain.server.models.Id(id))
            ?: throw NotFoundException("DeleteServerCommand#Execute: サーバー(Id: ${id})が見つかりませんでした。")
        repository.delete(server)
    }
}