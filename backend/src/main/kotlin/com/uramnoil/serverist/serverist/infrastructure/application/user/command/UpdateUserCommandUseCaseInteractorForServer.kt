package com.uramnoil.serverist.serverist.infrastructure.application.user.command

import com.uramnoil.serverist.application.user.commands.UpdateUserCommandUseCaseInputPortForServer
import com.uramnoil.serverist.application.user.commands.UpdateUserCommandUseCaseOutputPort
import com.uramnoil.serverist.domain.common.exception.UserNotFoundByIdException
import com.uramnoil.serverist.domain.common.user.Id
import com.uramnoil.serverist.domain.serverist.models.user.AccountId
import com.uramnoil.serverist.domain.serverist.models.user.Description
import com.uramnoil.serverist.domain.serverist.models.user.Name
import com.uramnoil.serverist.domain.serverist.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext

class UpdateUserCommandUseCaseInteractorForServer(
    private val repository: UserRepository,
    private val outputPort: UpdateUserCommandUseCaseOutputPort,
    private val coroutineContext: CoroutineContext
) : UpdateUserCommandUseCaseInputPortForServer {
    override fun execute(id: UUID, accountId: String, name: String, description: String) {
        CoroutineScope(coroutineContext).launch {
            val user = repository.findById(Id(id)).getOrElse {
                outputPort.handle(Result.failure(it))
                return@launch
            }

            user ?: run {
                outputPort.handle(Result.failure(UserNotFoundByIdException(id.toString())))
                return@launch
            }

            user.apply {
                this.accountId = AccountId(accountId)
                this.name = Name(name)
                this.description = Description(description)
            }

            outputPort.handle(repository.update(user))
            return@launch
        }
    }
}