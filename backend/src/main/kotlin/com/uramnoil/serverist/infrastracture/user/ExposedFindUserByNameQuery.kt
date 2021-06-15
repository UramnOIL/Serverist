package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.user.User
import com.uramnoil.serverist.application.user.queries.FindUserByNameDto
import com.uramnoil.serverist.application.user.queries.FindUserByNameQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedFindUserByNameQuery : FindUserByNameQuery {
    override suspend fun execute(dto: FindUserByNameDto): User? {
        return newSuspendedTransaction {
            Users.select { Users.name.lowerCase() eq dto.name.toLowerCase() }.firstOrNull()
        }?.let(ResultRow::toApplicationUser)
    }
}