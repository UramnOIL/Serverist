package com.uramnoil.serverist.infrastracture

import com.uramnoil.serverist.application.service.usecases.server.commands.CreateServerCommand
import com.uramnoil.serverist.application.service.usecases.server.commands.CreateServerDto
import com.uramnoil.serverist.domain.service.models.user.Id
import com.uramnoil.serverist.domain.service.repositories.NotFoundException
import com.uramnoil.serverist.domain.service.repositories.UserRepository
import com.uramnoil.serverist.domain.service.services.server.CreateServerService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.coroutines.CoroutineContext

class ExposedCreateServerCommand(
    private val database: Database,
    private val repository: UserRepository,
    private val service: CreateServerService,
    context: CoroutineContext
) : CreateServerCommand, CoroutineScope by CoroutineScope(context) {
    override fun execute(dto: CreateServerDto) {
        launch {
            newSuspendedTransaction(db = database) {
                val user = repository.findByIdAsync(Id(dto.ownerId)).await()
                    ?: throw NotFoundException("CreateServerCommand#execute: ユーザー(Id: ${dto.ownerId})が見つかりませんでした。")
                service.newAsync(dto.name, user, dto.address, dto.port, dto.description).await()
                commit()
            }
        }
    }
}