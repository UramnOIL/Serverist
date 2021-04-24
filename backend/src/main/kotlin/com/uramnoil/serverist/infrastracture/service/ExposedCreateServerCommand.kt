package com.uramnoil.serverist.infrastracture.service

import com.uramnoil.serverist.application.service.usecases.server.commands.CreateServerCommand
import com.uramnoil.serverist.application.service.usecases.server.commands.CreateServerDto
import com.uramnoil.serverist.domain.models.server.Address
import com.uramnoil.serverist.domain.models.server.Description
import com.uramnoil.serverist.domain.models.server.Name
import com.uramnoil.serverist.domain.models.server.Port
import com.uramnoil.serverist.domain.repositories.NotFoundException
import com.uramnoil.serverist.domain.repositories.UserRepository
import com.uramnoil.serverist.domain.services.server.CreateServerService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.coroutines.CoroutineContext
import com.uramnoil.serverist.domain.models.user.Id as UserId

class ExposedCreateServerCommand(
    private val database: Database,
    private val repository: UserRepository,
    private val service: CreateServerService,
    context: CoroutineContext
) : CreateServerCommand, CoroutineScope by CoroutineScope(context) {
    override fun execute(dto: CreateServerDto) {
        launch {
            newSuspendedTransaction(db = database) {
                val user = repository.findByIdAsync(UserId(dto.ownerId)).await()
                    ?: throw NotFoundException("CreateServerCommand#execute: ユーザー(Id: ${dto.ownerId})が見つかりませんでした。")
                service.newAsync(
                    Name(dto.name),
                    user,
                    Address(dto.address),
                    Port(dto.port),
                    Description(dto.description)
                ).await()
                commit()
            }
        }
    }
}