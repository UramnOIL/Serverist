package com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.queries

import com.uramnoil.serverist.application.unauthenticated.queries.FindUserByIdQueryUseCaseInputPort
import com.uramnoil.serverist.application.unauthenticated.queries.FindUserByIdQueryUseCaseOutputPort
import com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.toApplication
import com.uramnoil.serverist.domain.auth.unauthenticated.models.Id
import com.uramnoil.serverist.domain.auth.unauthenticated.repositories.UserRepository
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
            val result = repository.findById(Id(id)).map {
                it?.toApplication()
            }
            outputPort.handle(result)
            return@launch
        }
    }
}