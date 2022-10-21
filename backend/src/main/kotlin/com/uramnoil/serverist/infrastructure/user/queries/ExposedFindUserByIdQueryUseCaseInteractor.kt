package com.uramnoil.serverist.infrastracture.user.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.user.queries.FindUserByIdQueryUseCaseInputPort
import com.uramnoil.serverist.application.user.queries.FindUserByIdQueryUseCaseOutputPort
import com.uramnoil.serverist.infrastructure.user.Users
import com.uramnoil.serverist.infrastracture.user.toApplicationUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.coroutines.CoroutineContext

class ExposedFindUserByIdQueryUseCaseInteractor(
    private val outputPort: FindUserByIdQueryUseCaseOutputPort,
    parentContext: CoroutineContext
) :
    FindUserByIdQueryUseCaseInputPort,
    CoroutineScope by CoroutineScope(parentContext + Job(parentContext.job)) {
    override fun execute(id: Uuid) {
        launch {
            val rowOrNull = kotlin.runCatching {
                newSuspendedTransaction {
                    Users.select { Users.id eq id }.firstOrNull()
                }
            }
            val result = rowOrNull.map { it?.let(ResultRow::toApplicationUser) }
            outputPort.handle(result)
        }
    }
}