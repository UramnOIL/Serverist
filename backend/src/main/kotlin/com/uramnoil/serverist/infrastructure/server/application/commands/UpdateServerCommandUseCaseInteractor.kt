package com.uramnoil.serverist.infrastracture.server.application.commands

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.server.commands.UpdateServerCommandUseCaseInputPort
import com.uramnoil.serverist.application.server.commands.UpdateServerCommandUseCaseOutputPort
import com.uramnoil.serverist.domain.server.models.*
import com.uramnoil.serverist.domain.server.repositories.ServerRepository
import io.ktor.features.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UpdateServerCommandUseCaseInteractor(
    private val outputPort: UpdateServerCommandUseCaseOutputPort,
    private val repository: ServerRepository,
    parentContext: CoroutineContext
) : UpdateServerCommandUseCaseInputPort, CoroutineScope by CoroutineScope(parentContext + Job(parentContext.job)) {
    override fun execute(
        id: Uuid,
        name: String,
        address: String?,
        port: Int?,
        description: String
    ) {
        launch {
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
            outputPort.handle(updateResult)
        }
    }
}