package com.uramnoil.serverist.infrastracture.user

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.user.User
import com.uramnoil.serverist.application.user.queries.FindUserByIdQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedFindUserByIdQuery : FindUserByIdQuery {
    override suspend fun execute(id: Uuid): User? = newSuspendedTransaction {
        Users.select { Users.id eq id }.firstOrNull()?.let(ResultRow::toApplicationUser)
    }
}