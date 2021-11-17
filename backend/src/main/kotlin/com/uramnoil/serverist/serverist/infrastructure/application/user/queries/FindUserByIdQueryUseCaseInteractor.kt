package com.uramnoil.serverist.serverist.infrastructure.application.user.queries

import com.uramnoil.serverist.application.user.queries.FindUserByIdQueryUseCaseInputPort
import com.uramnoil.serverist.application.user.queries.FindUserByIdQueryUseCaseOutputPort
import com.uramnoil.serverist.domain.common.user.Id
import com.uramnoil.serverist.domain.serverist.repositories.UserRepository
import com.uramnoil.serverist.serverist.infrastructure.application.user.toApplicationUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext

class FindUserByIdQueryUseCaseInteractor(
    private val repository: UserRepository,
    private val outputPort: FindUserByIdQueryUseCaseOutputPort,
    private val coroutineContext: CoroutineContext
) : FindUserByIdQueryUseCaseInputPort {
    override fun execute(id: UUID) {
        CoroutineScope(coroutineContext).launch {
            outputPort.handle(repository.findById(Id(id)).map { it?.toApplicationUser() })
            return@launch
        }
    }
}