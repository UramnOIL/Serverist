package com.uramnoil.serverist.infrastracture.service

import com.uramnoil.serverist.application.server.commands.UpdateServerCommand
import com.uramnoil.serverist.application.server.commands.UpdateServerDto
import com.uramnoil.serverist.domain.repositories.NotFoundException
import com.uramnoil.serverist.domain.repositories.ServerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.coroutines.CoroutineContext

class ExposedUpdateServerCommand(
    private val database: Database,
    private val repository: ServerRepository,
    context: CoroutineContext
) : UpdateServerCommand, CoroutineScope by CoroutineScope(context) {
    override fun execute(dto: UpdateServerDto) {
        launch {
            newSuspendedTransaction(db = database) {
                val server = repository.findByIdAsync(com.uramnoil.serverist.domain.models.server.Id(dto.id)).await()
                    ?: throw NotFoundException("UpdateServerCommand#excecute: サーバー(Id: ${dto.id})が見つかりませんでした。")
                server.apply {
                    name = com.uramnoil.serverist.domain.models.server.Name(dto.name)
                    address = com.uramnoil.serverist.domain.models.server.Address(dto.address)
                    port = com.uramnoil.serverist.domain.models.server.Port(dto.port)
                    description = com.uramnoil.serverist.domain.models.server.Description(dto.description)
                }
                repository.storeAsync(server).await()
                commit()
            }
        }
    }
}