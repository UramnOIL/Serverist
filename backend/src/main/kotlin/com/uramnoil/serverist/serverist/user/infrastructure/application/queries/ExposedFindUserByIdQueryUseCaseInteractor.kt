package com.uramnoil.serverist.serverist.user.infrastructure.application.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.serverist.user.application.User
import com.uramnoil.serverist.serverist.user.application.queries.FindUserByIdQueryUseCaseInputPort
import com.uramnoil.serverist.serverist.user.infrastructure.Users
import com.uramnoil.serverist.serverist.user.infrastructure.application.toApplicationUser
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedFindUserByIdQueryUseCaseInteractor : FindUserByIdQueryUseCaseInputPort {
    override suspend fun execute(id: Uuid): Result<User?> {
        val rowOrNull = kotlin.runCatching {
            newSuspendedTransaction {
                Users.select { Users.id eq id }.firstOrNull()
            }
        }
        return rowOrNull.map { it?.let(ResultRow::toApplicationUser) }
    }
}