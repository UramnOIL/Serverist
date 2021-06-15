package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.application.server.commands.UpdateServerCommand
import com.uramnoil.serverist.application.server.commands.UpdateServerCommandDto
import com.uramnoil.serverist.domain.models.server.*
import com.uramnoil.serverist.domain.repositories.NotFoundException
import com.uramnoil.serverist.domain.repositories.ServerRepository
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*

class UpdateServerCommandImpl(
    private val repository: ServerRepository
) : UpdateServerCommand {
    override suspend fun execute(dto: UpdateServerCommandDto) {
        newSuspendedTransaction {
            val server = repository.findById(Id(UUID.fromString(dto.id)))
                ?: throw NotFoundException("UpdateServerCommand#excecute: サーバー(Id: ${dto.id})が見つかりませんでした。")
            server.apply {
                name = Name(dto.name)
                address = Address(dto.address)
                port = Port(dto.port)
                description = Description(dto.description)
            }
            repository.store(server)
            commit()
        }
    }
}