package com.uramnoil.serverist.serverist.infrastructures.application.usecase.user.queries

import com.uramnoil.serverist.serverist.application.usecases.user.queries.FindUserByAccountIdQueryUseCaseInputPort
import com.uramnoil.serverist.serverist.application.usecases.user.queries.User
import com.uramnoil.serverist.serverist.infrastructures.application.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.coroutines.CoroutineContext

class ExposedFindUserByAccountIdQueryUseCaseInteractor() : FindUserByAccountIdQueryUseCaseInputPort {
    override suspend fun execute(accountId: String): Result<User?> {
        val row = kotlin.runCatching {
            newSuspendedTransaction {
                Users.select { Users.accountId eq accountId }.firstOrNull()
            }
        }
        return row.map { it?.toApplicationUser() }
    }
}
