package com.uramnoil.serverist.infrastracture.server

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.server.commands.UpdateServerCommandInputPort
import com.uramnoil.serverist.domain.kernel.NotFoundException
import com.uramnoil.serverist.domain.server.models.*
import com.uramnoil.serverist.domain.server.repositories.ServerRepository

class UpdateServerCommandInteractor(
    private val repository: ServerRepository
) : UpdateServerCommandInputPort {
    override suspend fun execute(
        id: Uuid,
        name: String,
        address: String?,
        port: Int?,
        description: String
    ): Result<Unit> {
        return repository.findById(Id(id)).map {
            it ?: throw NotFoundException("UpdateServerCommandInteractor#excecute: サーバー(Id: ${id})が見つかりませんでした。")
            it.apply {
                this.name = Name(name)
                this.address = Address(address)
                this.port = Port(port)
                this.description = Description(description)
            }
            repository.update(it)
            Unit
        }
    }
}