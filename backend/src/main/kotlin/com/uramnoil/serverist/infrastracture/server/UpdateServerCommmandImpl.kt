package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.application.server.commands.UpdateServerCommand
import com.uramnoil.serverist.application.server.commands.UpdateServerDto
import com.uramnoil.serverist.domain.models.server.*
import com.uramnoil.serverist.domain.repositories.NotFoundException
import com.uramnoil.serverist.domain.repositories.ServerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*
import kotlin.coroutines.CoroutineContext

class UpdateServerCommmandImpl(
    private val database: Database,
    private val repository: ServerRepository,
    context: CoroutineContext
) : UpdateServerCommand, CoroutineScope by CoroutineScope(context) {
    override fun execute(dto: UpdateServerDto) {
        launch {
            newSuspendedTransaction(db = database) {
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
}