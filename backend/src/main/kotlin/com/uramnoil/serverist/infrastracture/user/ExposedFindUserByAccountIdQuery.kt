package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.user.User
import com.uramnoil.serverist.application.user.queries.FindUserByAccountIdQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedFindUserByAccountIdQuery : FindUserByAccountIdQuery {
    override suspend fun execute(accountId: String): User? {
        return newSuspendedTransaction {
            Users.select { Users.accountId eq accountId }.firstOrNull()
        }?.let(ResultRow::toApplicationUser)
    }
}