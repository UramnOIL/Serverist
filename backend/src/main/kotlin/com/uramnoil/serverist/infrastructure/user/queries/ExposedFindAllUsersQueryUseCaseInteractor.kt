package com.uramnoil.serverist.infrastracture.user.queries

import com.uramnoil.serverist.application.user.queries.FindAllUsersQueryUseCaseInputPort
import com.uramnoil.serverist.application.user.queries.FindAllUsersQueryUseCaseOutputPort
import com.uramnoil.serverist.infrastructure.user.Users
import com.uramnoil.serverist.infrastracture.user.toApplicationUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.coroutines.CoroutineContext

class ExposedFindAllUsersQueryUseCaseInteractor(
    private val outputPort: FindAllUsersQueryUseCaseOutputPort,
    parentContext: CoroutineContext,
) :
    FindAllUsersQueryUseCaseInputPort,
    CoroutineScope by CoroutineScope(parentContext + Job(parentContext.job)) {
    override fun execute() {
        launch {
            val result = runCatching {
                newSuspendedTransaction {
                    Users.selectAll().map { it.toApplicationUser() }
                }
            }
            outputPort.handle(result)
        }
    }
}