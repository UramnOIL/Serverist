package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.user.commands.UpdateUserCommand
import com.uramnoil.serverist.application.user.commands.UpdateUserDto
import com.uramnoil.serverist.domain.models.user.Description
import com.uramnoil.serverist.domain.models.user.Id
import com.uramnoil.serverist.domain.models.user.Name
import com.uramnoil.serverist.domain.repositories.NotFoundException
import com.uramnoil.serverist.domain.repositories.UserRepository
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
                val user = repository.findById(Id(dto.id))?.apply {
                    name = Name(dto.name)
                    description = Description(dto.description)
                } ?: throw NotFoundException("UpdateUserCommand#execute: ユーザー(ID: ${dto.id})が見つかりませんでした。")

                repository.store(user)
            }
        }
    }
}