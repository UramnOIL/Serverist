package com.uramnoil.serverist.infrastracture.server

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.server.commands.UpdateServerCommand
import com.uramnoil.serverist.domain.kernel.NotFoundException
import com.uramnoil.serverist.domain.server.models.*
import com.uramnoil.serverist.domain.server.repositories.ServerRepository
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UpdateServerCommandImpl(
    private val repository: ServerRepository
) : UpdateServerCommand {
    override suspend fun execute(id: Uuid, name: String, address: String?, port: Int?, description: String) {
        newSuspendedTransaction {
            val server = repository.findById(Id(id))
                ?: throw NotFoundException("UpdateServerCommand#excecute: サーバー(Id: ${id})が見つかりませんでした。")
            server.apply {
                this.name = Name(name)
                this.address = Address(address)
                this.port = Port(port)
                this.description = Description(description)
            }
            repository.update(server)
            commit()
        }
    }
}