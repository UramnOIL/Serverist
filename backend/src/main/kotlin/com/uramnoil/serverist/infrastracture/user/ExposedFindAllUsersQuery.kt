package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.kernel.User
import com.uramnoil.serverist.application.user.queries.FindAllUsersQuery
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedFindAllUsersQuery : FindAllUsersQuery {
    override suspend fun execute(): List<User> = newSuspendedTransaction {
        Users.selectAll().map {
            User(it[Users.id].value, it[Users.accountId], it[Users.name], it[Users.description])
        }
    }
}