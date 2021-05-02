package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.user.User
import com.uramnoil.serverist.application.user.queries.FindUserByNameDto
import com.uramnoil.serverist.application.user.queries.FindUserByNameQuery
import com.uramnoil.serverist.domain.services.user.UserFactory
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedFindUserByNameQuery(
    private val database: Database
) : FindUserByNameQuery {
    override suspend fun execute(dto: FindUserByNameDto): User? {
        val user = newSuspendedTransaction(db = database) {
            val result = Users.select { Users.name.lowerCase() eq dto.name.toLowerCase() }.firstOrNull()
                ?: return@newSuspendedTransaction null

            result.let {
                UserFactory.create(
                    it[Users.id].value,
                    it[Users.accountId],
                    it[Users.name],
                    it[Users.description],
                )
            }
        }

        return user?.let {
            User(
                id = it.id.value.toString(),
                accountId = it.accountId.value,
                name = it.name.value,
                description = it.description.value
            )
        }
    }
}