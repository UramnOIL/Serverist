package com.uramnoil.serverist.infrastracture.unauthenticateduser

import com.uramnoil.serverist.domain.unauthenticateduser.models.Id
import com.uramnoil.serverist.domain.unauthenticateduser.models.UnauthenticatedUser
import com.uramnoil.serverist.domain.unauthenticateduser.repositories.UnauthenticatedUserRepository
import com.uramnoil.serverist.infrastracture.user.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedUnauthenticatedUserRepository : UnauthenticatedUserRepository {
    override suspend fun insert(user: UnauthenticatedUser) {
        newSuspendedTransaction {
            Users.insert {
                it[id] = user.id.value
                it[accountId] = user.accountId.value
                it[email] = user.email.value
                it[hashedPassword] = user.hashedPassword.value
            }
            commit()
        }
    }

    override suspend fun update(user: UnauthenticatedUser) {
        newSuspendedTransaction {
            Users.update({ Users.id eq user.id.value }) {
                it[accountId] = user.accountId.value
                it[email] = user.email.value
                it[hashedPassword] = user.hashedPassword.value
                commit()
            }
        }
    }

    override suspend fun delete(user: UnauthenticatedUser) {
        newSuspendedTransaction {
            Users.deleteWhere { Users.id eq user.id.value }
            commit()
        }
    }

    override suspend fun findById(id: Id): UnauthenticatedUser? {
        return newSuspendedTransaction {
            Users.select { Users.id eq id.value }.firstOrNull()
        }?.let(ResultRow::toDomainUnauthenticatedUser)
    }
}