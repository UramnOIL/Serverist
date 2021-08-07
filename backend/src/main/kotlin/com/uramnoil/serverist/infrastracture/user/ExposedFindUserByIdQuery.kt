package com.uramnoil.serverist.infrastracture.user

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.user.queries.FindUserByIdQueryInputPort
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedFindUserByIdQuery : FindUserByIdQueryInputPort {
    override suspend fun execute(id: Uuid) = kotlin.runCatching {
        newSuspendedTransaction {
            Users.select { Users.id eq id }.firstOrNull()?.let(ResultRow::toApplicationUser)
        }
    }
}