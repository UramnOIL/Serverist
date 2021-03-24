package com.uramnoil.serverist.infrastracture

import com.uramnoil.serverist.application.usecases.server.commands.DeleteServerCommand
import com.uramnoil.serverist.application.usecases.server.commands.DeleteServerDto
import com.uramnoil.serverist.domain.service.models.server.Id
import com.uramnoil.serverist.domain.service.repositories.NotFoundException
import com.uramnoil.serverist.domain.service.repositories.ServerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.coroutines.CoroutineContext

class ExposedDeleteServerCommand(
    private val database: Database,
    private val serverRepository: ServerRepository,
    private val context: CoroutineContext
) : DeleteServerCommand, CoroutineScope by CoroutineScope(context) {
    override fun execute(dto: DeleteServerDto) {
        launch {
            newSuspendedTransaction {
                val server = serverRepository.findByIdAsync(Id(dto.id)).await()
                    ?: throw NotFoundException("DeleteServerCommand#Execute: サーバー(Id: ${dto.id})が見つかりませんでした。")
                serverRepository.deleteAsync(server).await()
                commit()
            }
        }
    }
}