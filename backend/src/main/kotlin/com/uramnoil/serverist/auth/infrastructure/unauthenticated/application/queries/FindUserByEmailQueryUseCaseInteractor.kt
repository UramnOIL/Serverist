package com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.queries

import com.uramnoil.serverist.application.unauthenticated.queries.FindUserByEmailQueryUseCaseInputPort
import com.uramnoil.serverist.application.unauthenticated.queries.FindUserByEmailQueryUseCaseOutputPort
import com.uramnoil.serverist.auth.infrastructure.unauthenticated.Users
import com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.toApplicationUnauthenticatedUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.coroutines.CoroutineContext

class FindUserByEmailQueryUseCaseInteractor(
    private val outputPort: FindUserByEmailQueryUseCaseOutputPort,
    private val coroutineContext: CoroutineContext
) : FindUserByEmailQueryUseCaseInputPort {
    override fun execute(email: String) {
        kotlin.runCatching {
            CoroutineScope(coroutineContext).launch {
                val row = newSuspendedTransaction {
                    Users.select { Users.email eq email }.firstOrNull()
                }
                outputPort.handle(Result.success(row?.toApplicationUnauthenticatedUser()))
            }
        }
    }
}