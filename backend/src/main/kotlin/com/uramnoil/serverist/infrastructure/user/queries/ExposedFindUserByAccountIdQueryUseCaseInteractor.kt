package com.uramnoil.serverist.infrastracture.user.queries

import com.uramnoil.serverist.application.user.queries.FindUserByAccountIdQueryUseCaseInputPort
import com.uramnoil.serverist.application.user.queries.FindUserByAccountIdQueryUseCaseOutputPort
import com.uramnoil.serverist.infrastructure.user.Users
import com.uramnoil.serverist.infrastracture.user.toApplicationUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.coroutines.CoroutineContext

class ExposedFindUserByAccountIdQueryUseCaseInteractor(
    private val outputPort: FindUserByAccountIdQueryUseCaseOutputPort,
    parentContext: CoroutineContext,
) :
    FindUserByAccountIdQueryUseCaseInputPort,
    CoroutineScope by CoroutineScope(parentContext + Job(parentContext.job)) {
    override fun execute(accountId: String) {
        launch {
            val row = kotlin.runCatching {
                newSuspendedTransaction {
                    Users.select { Users.accountId eq accountId }.firstOrNull()
                }
            }
            val result = row.map { it?.toApplicationUser() }
            outputPort.handle(result)
        }
    }
}