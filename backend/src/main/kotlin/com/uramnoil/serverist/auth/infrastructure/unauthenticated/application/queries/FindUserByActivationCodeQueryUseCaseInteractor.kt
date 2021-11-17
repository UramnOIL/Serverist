package com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.queries

import com.uramnoil.serverist.application.unauthenticated.queries.FindUserByActivationCodeQueryUseCaseInputPort
import com.uramnoil.serverist.application.unauthenticated.queries.FindUserByActivationCodeQueryUseCaseOutputPort
import com.uramnoil.serverist.auth.infrastructure.unauthenticated.Users
import com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.toApplicationUnauthenticatedUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*
import kotlin.coroutines.CoroutineContext

class FindUserByActivationCodeQueryUseCaseInteractor(
    private val outputPort: FindUserByActivationCodeQueryUseCaseOutputPort,
    private val coroutineContext: CoroutineContext
) : FindUserByActivationCodeQueryUseCaseInputPort {
    override fun execute(activationCode: UUID) {
        kotlin.runCatching {
            CoroutineScope(coroutineContext).launch {
                val row = newSuspendedTransaction {
                    Users.select { Users.activateCode eq activationCode }.firstOrNull()
                }
                outputPort.handle(Result.success(row?.toApplicationUnauthenticatedUser()))
            }
        }
    }
}