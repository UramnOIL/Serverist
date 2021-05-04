package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.domain.models.user.Id
import com.uramnoil.serverist.domain.models.user.User
import com.uramnoil.serverist.domain.repositories.UserRepository
import com.uramnoil.serverist.domain.services.user.UserFactory
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedUserRepository(private val database: Database) : UserRepository {
    private val logger = StdOutSqlLogger

    override suspend fun store(user: User) = newSuspendedTransaction(db = database) {
        addLogger(logger)

        Users.update({ Users.id eq user.id.value }) {
            it[name] = user.name.value
            it[description] = user.description.value
        }
        commit()
    }

    override suspend fun delete(user: User) = newSuspendedTransaction(db = database) {
        addLogger(logger)

        Users.deleteWhere { Users.id eq user.id.value }
        commit()
    }

    override suspend fun findById(id: Id): User? = newSuspendedTransaction(db = database) {
        addLogger(logger)

        val query = Users.select { Users.id eq id.value }.firstOrNull() ?: return@newSuspendedTransaction null

        query.let {
            UserFactory.create(
                id = it[Users.id].value,
                accountId = it[Users.accountId],
                name = it[Users.name],
                description = it[Users.description]
            )
        }
    }
}
