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
    private val repository: ServerRepository,
    context: CoroutineContext
) : DeleteServerCommand, CoroutineScope by CoroutineScope(context) {
    override fun execute(dto: DeleteServerDto) {
        launch {
            newSuspendedTransaction(db = database) {
                val server = repository.findByIdAsync(Id(dto.id)).await()
                    ?: throw NotFoundException("DeleteServerCommand#Execute: サーバー(Id: ${dto.id})が見つかりませんでした。")
                repository.deleteAsync(server).await()
                commit()
            }
        }
    }
}