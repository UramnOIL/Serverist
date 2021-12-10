package com.uramnoil.serverist.serverist.infrastructure.application.server.commands

import com.uramnoil.serverist.domain.serverist.models.server.*
import com.uramnoil.serverist.domain.serverist.repositories.ServerRepository
import com.uramnoil.serverist.serverist.application.server.commands.UpdateServerCommandUseCaseInputPort
import com.uramnoil.serverist.serverist.application.server.commands.UpdateServerCommandUseCaseOutputPort
import io.ktor.features.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext

class UpdateServerCommandUseCaseInteractor(
    private val repository: ServerRepository,
    private val outputPort: UpdateServerCommandUseCaseOutputPort,
    private val coroutineContext: CoroutineContext
) : UpdateServerCommandUseCaseInputPort {
    override fun execute(
        id: UUID,
        name: String,
        host: String?,
        port: UShort?,
        description: String
    ) {
        CoroutineScope(coroutineContext).launch {
            val findResult = repository.findById(Id(id))
            val updateResult = findResult.mapCatching { server ->
                server ?: throw NotFoundException("UpdateServerCommandInteractor#excecute: サーバー(Id: ${id})が見つかりませんでした。")
                server.apply {
                    this.name = Name(name)
                    this.host = host?.let { Host(it) }
                    this.port = port?.let { Port(it) }
                    this.description = Description(description)
                }
                repository.update(server).getOrThrow()
            }
            outputPort.handle(updateResult)
            return@launch
        }
    }
}