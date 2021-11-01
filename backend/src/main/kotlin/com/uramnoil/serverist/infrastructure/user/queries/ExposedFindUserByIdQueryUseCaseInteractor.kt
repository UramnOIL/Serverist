package com.uramnoil.serverist.infrastructure.user.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.user.User
import com.uramnoil.serverist.application.user.queries.FindUserByIdQueryUseCaseInputPort
import com.uramnoil.serverist.infrastracture.user.toApplicationUser
import com.uramnoil.serverist.infrastructure.user.Users
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