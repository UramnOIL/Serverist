package com.uramnoil.serverist.auth.infrastructure.application.unauthenticated.queries

import com.uramnoil.serverist.application.unauthenticated.queries.FindUserByActivationCodeQueryUseCaseInputPort
import com.uramnoil.serverist.application.unauthenticated.queries.FindUserByActivationCodeQueryUseCaseOutputPort
import com.uramnoil.serverist.auth.infrastructure.UnauthenticatedUsers
import com.uramnoil.serverist.auth.infrastructure.toApplicationUnauthenticatedUser
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
                    UnauthenticatedUsers.select { UnauthenticatedUsers.activateCode eq activationCode }.firstOrNull()
                }
                outputPort.handle(Result.success(row?.toApplicationUnauthenticatedUser()))
            }
        }
    }
}