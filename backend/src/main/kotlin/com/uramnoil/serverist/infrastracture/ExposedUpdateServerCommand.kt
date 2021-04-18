package com.uramnoil.serverist.infrastracture

import com.uramnoil.serverist.application.service.usecases.server.commands.UpdateServerCommand
import com.uramnoil.serverist.application.service.usecases.server.commands.UpdateServerDto
import com.uramnoil.serverist.domain.service.models.server.*
import com.uramnoil.serverist.domain.service.repositories.NotFoundException
import com.uramnoil.serverist.domain.service.repositories.ServerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.coroutines.CoroutineContext

class ExposedUpdateServerCommand(
    private val database: Database,
    private val sepository: ServerRepository,
    context: CoroutineContext
) : UpdateServerCommand, CoroutineScope by CoroutineScope(context) {
    override fun execute(dto: UpdateServerDto) {
        launch {
            newSuspendedTransaction(db = database) {
                val server = sepository.findByIdAsync(Id(dto.id)).await()
                    ?: throw NotFoundException("UpdateServerCommand#excecute: サーバー(Id: ${dto.id})が見つかりませんでした。")
                server.apply {
                    name = Name(dto.name)
                    address = Address(dto.address)
                    port = Port(dto.port)
                    description = Description(dto.description)
                }
                sepository.storeAsync(server).await()
                commit()
            }
        }
    }
}