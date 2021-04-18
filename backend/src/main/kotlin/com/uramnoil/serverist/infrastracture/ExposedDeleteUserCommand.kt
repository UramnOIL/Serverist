package com.uramnoil.serverist.infrastracture

import com.uramnoil.serverist.application.service.usecases.user.commands.DeleteUserCommand
import com.uramnoil.serverist.application.service.usecases.user.commands.DeleteUserCommandDto
import com.uramnoil.serverist.domain.service.models.user.Id
import com.uramnoil.serverist.domain.service.repositories.NotFoundException
import com.uramnoil.serverist.domain.service.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ExposedDeleteUserCommand(private val repository: UserRepository, context: CoroutineContext) :
    DeleteUserCommand, CoroutineScope by CoroutineScope(context) {
    override fun execute(dto: DeleteUserCommandDto) {
        launch {
            repository.findByIdAsync(Id(dto.id)).await()?.let {
                repository.deleteAsync(it)
            } ?: throw NotFoundException("DeleteUserCommand#execute: ユーザー(ID: ${dto.id})が見つかりませんでした。")
        }
    }
}