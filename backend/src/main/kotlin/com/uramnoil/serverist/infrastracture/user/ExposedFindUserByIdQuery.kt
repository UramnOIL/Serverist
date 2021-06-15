package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.user.User
import com.uramnoil.serverist.application.user.queries.FindUserByIdQuery
import com.uramnoil.serverist.application.user.queries.FindUserByIdQueryDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedFindUserByIdQuery : FindUserByIdQuery {
    override suspend fun execute(dto: FindUserByIdQueryDto): User? = newSuspendedTransaction {
        Users.select { Users.id eq dto.id }.firstOrNull()?.let(ResultRow::toApplicationUser)
    }
}