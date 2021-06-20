package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.user.User
import com.uramnoil.serverist.application.user.queries.FindUserByIdQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*

class ExposedFindUserByIdQuery : FindUserByIdQuery {
    override suspend fun execute(id: UUID): User? = newSuspendedTransaction {
        Users.select { Users.id eq id }.firstOrNull()?.let(ResultRow::toApplicationUser)
    }
}