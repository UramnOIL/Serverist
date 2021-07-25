package com.uramnoil.serverist.infrastracture.unauthenticateduser

import com.uramnoil.serverist.domain.unauthenticateduser.models.Id
import com.uramnoil.serverist.domain.unauthenticateduser.models.UnauthenticatedUser
import com.uramnoil.serverist.domain.unauthenticateduser.repositories.UnauthenticatedUserRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedUnauthenticatedUserRepository : UnauthenticatedUserRepository {
    override suspend fun insert(user: UnauthenticatedUser) = kotlin.runCatching {
        newSuspendedTransaction {
            UnauthenticatedUsers.insert {
                it[id] = user.id.value
                it[accountId] = user.accountId.value
                it[email] = user.email.value
                it[hashedPassword] = user.hashedPassword.value
            }
            commit()
        }
    }

    override suspend fun update(user: UnauthenticatedUser): Result<Unit> = kotlin.runCatching {
        newSuspendedTransaction {
            UnauthenticatedUsers.update({ UnauthenticatedUsers.id eq user.id.value }) {
                it[accountId] = user.accountId.value
                it[email] = user.email.value
                it[hashedPassword] = user.hashedPassword.value
                commit()
            }
        }
    }

    override suspend fun delete(user: UnauthenticatedUser) = kotlin.runCatching {
        newSuspendedTransaction {
            UnauthenticatedUsers.deleteWhere { UnauthenticatedUsers.id eq user.id.value }
            commit()
        }
    }

    override suspend fun findById(id: Id) = kotlin.runCatching {
        newSuspendedTransaction {
            UnauthenticatedUsers.select { UnauthenticatedUsers.id eq id.value }.firstOrNull()
        }?.let(ResultRow::toDomainUnauthenticatedUser)
    }
}