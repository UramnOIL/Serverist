package com.uramnoil.serverist.infrastracture

import com.uramnoil.serverist.application.usecases.user.commands.UpdateUserCommand
import com.uramnoil.serverist.application.usecases.user.commands.UpdateUserDto
import com.uramnoil.serverist.domain.service.models.user.Description
import com.uramnoil.serverist.domain.service.models.user.Id
import com.uramnoil.serverist.domain.service.models.user.Name
import com.uramnoil.serverist.domain.service.repositories.NotFoundException
import com.uramnoil.serverist.domain.service.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.coroutines.CoroutineContext

class ExposedUpdateUserCommand(
    private val database: Database,
    private val repository: UserRepository,
    context: CoroutineContext
) : UpdateUserCommand,
    CoroutineScope by CoroutineScope(context) {
    override fun execute(dto: UpdateUserDto) {
        launch {
            newSuspendedTransaction(db = database) {
                repository.findByIdAsync(Id(dto.id)).await()?.apply {
                    name = Name(dto.name)
                    description = Description(dto.description)

                    repository.storeAsync(this).await()
                } ?: throw NotFoundException("UpdateUserCommand#execute: ユーザー(ID: ${dto.id})が見つかりませんでした。")
            }
        }
    }
}