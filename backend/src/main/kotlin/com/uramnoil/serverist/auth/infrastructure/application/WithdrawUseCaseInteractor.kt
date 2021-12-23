package com.uramnoil.serverist.auth.infrastructure.application

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.auth.application.WithdrawUseCaseInputPort
import com.uramnoil.serverist.auth.application.WithdrawUseCaseOutputPort
import com.uramnoil.serverist.domain.auth.authenticated.repositories.UserRepository
import com.uramnoil.serverist.domain.common.exception.UserNotFoundByIdException
import com.uramnoil.serverist.domain.common.user.Id
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class WithdrawUseCaseInteractor(
    private val repository: UserRepository,
    coroutineContext: CoroutineContext,
    private val outputPort: WithdrawUseCaseOutputPort,
) : WithdrawUseCaseInputPort, CoroutineScope by CoroutineScope(coroutineContext) {
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
