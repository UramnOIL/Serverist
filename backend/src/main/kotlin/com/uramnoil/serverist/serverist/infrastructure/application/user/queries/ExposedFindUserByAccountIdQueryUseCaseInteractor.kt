package com.uramnoil.serverist.serverist.infrastructure.application.user.queries

import com.uramnoil.serverist.application.user.queries.FindUserByAccountIdQueryUseCaseInputPort
import com.uramnoil.serverist.application.user.queries.FindUserByAccountIdQueryUseCaseOutputPort
import com.uramnoil.serverist.serverist.infrastructure.Users
import com.uramnoil.serverist.serverist.infrastructure.toApplicationUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.coroutines.CoroutineContext

class ExposedFindUserByAccountIdQueryUseCaseInteractor(
    private val outputPort: FindUserByAccountIdQueryUseCaseOutputPort,
    private val coroutineContext: CoroutineContext
) : FindUserByAccountIdQueryUseCaseInputPort {
    override fun execute(accountId: String) {
        CoroutineScope(coroutineContext).launch {
            val row = kotlin.runCatching {
                newSuspendedTransaction {
                    Users.select { Users.accountId eq accountId }.firstOrNull()
                }
            }
            outputPort.handle(row.map { it?.toApplicationUser() })
            return@launch
        }
    }
}