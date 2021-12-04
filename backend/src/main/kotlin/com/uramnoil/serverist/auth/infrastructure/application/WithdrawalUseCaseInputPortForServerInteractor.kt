package com.uramnoil.serverist.auth.infrastructure.application

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.auth.WithdrawalUseCaseInputPortForServer
import com.uramnoil.serverist.application.auth.WithdrawalUseCaseOutputPort
import com.uramnoil.serverist.domain.auth.authenticated.repositories.UserRepository
import com.uramnoil.serverist.domain.common.exception.UserNotFoundByIdException
import com.uramnoil.serverist.domain.common.user.Id
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class WithdrawalUseCaseInputPortForServerInteractor(
    private val repository: UserRepository,
    coroutineContext: CoroutineContext,
    private val outputPort: WithdrawalUseCaseOutputPort,
) : WithdrawalUseCaseInputPortForServer, CoroutineScope by CoroutineScope(coroutineContext) {
    override fun execute(id: Uuid) {
        launch {
            val userResult = repository.findById(Id(id))

            val user = userResult.getOrElse {
                outputPort.handle(Result.failure(it))
                return@launch
            }

            user ?: kotlin.run {
                outputPort.handle(Result.failure(UserNotFoundByIdException(id.toString())))
                return@launch
            }

            outputPort.handle(repository.delete(user))
        }
    }
}