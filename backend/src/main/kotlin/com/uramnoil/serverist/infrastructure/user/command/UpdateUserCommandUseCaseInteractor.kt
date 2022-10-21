package com.uramnoil.serverist.infrastructure.user.command

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.user.commands.UpdateUserCommandUseCaseInputPort
import com.uramnoil.serverist.application.user.commands.UpdateUserCommandUseCaseOutputPort
import com.uramnoil.serverist.domain.common.exception.NotFoundException
import com.uramnoil.serverist.domain.common.user.UserId
import com.uramnoil.serverist.domain.user.models.Description
import com.uramnoil.serverist.domain.user.models.Name
import com.uramnoil.serverist.domain.user.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UpdateUserCommandUseCaseInteractor(
    private val outputPort: UpdateUserCommandUseCaseOutputPort,
    private val repository: UserRepository,
    parentCoroutine: CoroutineContext
) : UpdateUserCommandUseCaseInputPort, CoroutineScope by CoroutineScope(parentCoroutine + Job(parentCoroutine.job)) {
    override fun execute(id: Uuid, accountId: String, name: String, description: String) {
        launch {
            val user = repository.findById(UserId(id)).getOrElse {
                outputPort.handle(Result.failure(it))
                return@launch
            }

            user ?: run {
                outputPort.handle(Result.failure(NotFoundException("id: ${id}のユーザー見つかりませんでした。")))
                return@launch
            }

            user.apply {
                this.name = Name(name)
                this.description = Description(description)
            }

            outputPort.handle(repository.update(user))
        }
    }
}