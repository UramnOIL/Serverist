package com.uramnoil.serverist.serverist.infrastructures.application.usecase.server.commands

import com.uramnoil.serverist.serverist.domain.models.server.Description
import com.uramnoil.serverist.serverist.domain.models.server.Host
import com.uramnoil.serverist.serverist.domain.models.server.Id
import com.uramnoil.serverist.serverist.domain.models.server.Name
import com.uramnoil.serverist.serverist.domain.models.server.Port
import com.uramnoil.serverist.serverist.application.usecases.server.commands.UpdateServerCommandUseCaseInputPort
import com.uramnoil.serverist.serverist.application.usecases.server.commands.UpdateServerCommandUseCaseOutputPort
import com.uramnoil.serverist.serverist.domain.repositories.ServerRepository
import io.ktor.server.plugins.NotFoundException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.coroutines.CoroutineContext

class UpdateServerCommandUseCaseInteractor(
    private val repository: ServerRepository,
) : UpdateServerCommandUseCaseInputPort {
    override suspend fun execute(
        id: UUID,
        name: String,
        host: String?,
        port: UShort?,
        description: String
    ): Result<Unit> {
            val findResult = repository.findById(Id(id))
            val updateResult = findResult.mapCatching { server ->
                server ?: throw NotFoundException("UpdateServerCommandInteractor#excecute: サーバー(Id: $id)が見つかりませんでした。")
                server.apply {
                    this.name = Name(name)
                    this.host = host?.let { Host(it) }
                    this.port = port?.let { Port(it) }
                    this.description = Description(description)
                }
                repository.update(server).getOrThrow()
            }
            return updateResult
    }
}
