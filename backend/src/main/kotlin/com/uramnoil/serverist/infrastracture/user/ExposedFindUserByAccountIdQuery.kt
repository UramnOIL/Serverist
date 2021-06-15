package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.kernel.User
import com.uramnoil.serverist.application.user.queries.FindUserByAccountIdQuery
import com.uramnoil.serverist.application.user.queries.FindUserByAccountIdQueryDto
import com.uramnoil.serverist.domain.services.user.UserFactory
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedFindUserByAccountIdQuery : FindUserByAccountIdQuery {
    override suspend fun execute(dto: FindUserByAccountIdQueryDto): User? {
        val userResult = newSuspendedTransaction {
            Users.select { Users.accountId eq dto.accountId }.firstOrNull()
        } ?: return null

        val user = userResult.let {
            UserFactory.create(
                it[Users.id].value,
                it[Users.accountId],
                it[Users.email],
                it[Users.hashedPassword],
                it[Users.name],
                it[Users.description]
            )
        }

        return user.let {
            User(
                id = it.id.value,
                accountId = it.accountId.value,
                name = it.name.value,
                description = it.description.value
            )
        }
    }
}