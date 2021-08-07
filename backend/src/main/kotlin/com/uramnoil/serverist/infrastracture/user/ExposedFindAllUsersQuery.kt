package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.user.queries.FindAllUsersQueryInputPort
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedFindAllUsersQuery : FindAllUsersQueryInputPort {
    override suspend fun execute() = kotlin.runCatching {
        newSuspendedTransaction {
            Users.selectAll().map(ResultRow::toApplicationUser)
        }
    }
}