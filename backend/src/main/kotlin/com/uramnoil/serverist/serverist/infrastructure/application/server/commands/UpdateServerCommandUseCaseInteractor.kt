package com.uramnoil.serverist.serverist.infrastructure.application.server.commands

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.domain.server.models.*
import com.uramnoil.serverist.domain.server.repositories.ServerRepository
import com.uramnoil.serverist.serverist.application.server.commands.UpdateServerCommandUseCaseInputPort
import io.ktor.features.*

class UpdateServerCommandUseCaseInteractor(
    private val repository: ServerRepository,
) : UpdateServerCommandUseCaseInputPort {
    override suspend fun execute(
        id: Uuid,
        name: String,
        address: String?,
        port: Int?,
        description: String
    ): Result<Unit> {
        val findResult = repository.findById(Id(id))
        val updateResult = findResult.mapCatching {
            it ?: throw NotFoundException("UpdateServerCommandInteractor#excecute: サーバー(Id: ${id})が見つかりませんでした。")
            it.apply {
                this.name = Name(name)
                this.address = Address(address)
                this.port = Port(port)
                this.description = Description(description)
            }
            repository.update(it).getOrThrow()
        }
        return updateResult
    }
}