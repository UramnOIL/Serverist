package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.user.User
import com.uramnoil.serverist.application.user.queries.FindUserByAccountIdQuery
import com.uramnoil.serverist.application.user.queries.FindUserByAccountIdQueryDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedFindUserByAccountIdQuery : FindUserByAccountIdQuery {
    override suspend fun execute(dto: FindUserByAccountIdQueryDto): User? {
        return newSuspendedTransaction {
            Users.select { Users.accountId eq dto.accountId }.firstOrNull()
        }?.let(ResultRow::toApplicationUser)
    }
}