package com.uramnoil.serverist.infrastracture.auth.unauthenticated.application.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.auth.unauthenticated.queries.FindUserByIdQueryUseCaseInputPort
import com.uramnoil.serverist.application.auth.unauthenticated.queries.FindUserByIdQueryUseCaseOutputPort
import com.uramnoil.serverist.domain.auth.unauthenticated.models.Id
import com.uramnoil.serverist.domain.auth.unauthenticated.repositories.UserRepository
import com.uramnoil.serverist.infrastructure.auth.unauthenticated.toApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FindUserByIdQueryUseCaseInteractor(
    private val outputPort: FindUserByIdQueryUseCaseOutputPort,
    private val repository: UserRepository,
    parentContext: CoroutineContext
) :
    FindUserByIdQueryUseCaseInputPort,
    CoroutineScope by CoroutineScope(parentContext + Job(parentContext.job)) {
    override fun execute(id: Uuid) {
        launch {
            val result = repository.findById(Id(id)).map {
                it?.toApplication()
            }
            outputPort.handle(result)
        }
    }
}