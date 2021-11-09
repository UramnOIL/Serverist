package com.uramnoil.serverist.serverist.infrastructure.application.user.queries

import com.uramnoil.serverist.serverist.application.user.User
import com.uramnoil.serverist.serverist.application.user.queries.FindUserByAccountIdQueryUseCaseInputPort
import com.uramnoil.serverist.serverist.infrastructure.Users
import com.uramnoil.serverist.serverist.user.infrastructure.application.toApplicationUser
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedFindUserByAccountIdQueryUseCaseInteractor : FindUserByAccountIdQueryUseCaseInputPort {
    override suspend fun execute(accountId: String): Result<User?> {
        val row = kotlin.runCatching {
            newSuspendedTransaction {
                Users.select { Users.accountId eq accountId }.firstOrNull()
            }
        }
        return row.map { it?.toApplicationUser() }
    }
}