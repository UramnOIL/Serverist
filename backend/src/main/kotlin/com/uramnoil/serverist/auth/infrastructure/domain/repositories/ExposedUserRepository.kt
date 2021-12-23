package com.uramnoil.serverist.auth.infrastructure.domain.repositories

import com.uramnoil.serverist.auth.infrastructure.UnauthenticatedUsers
import com.uramnoil.serverist.auth.infrastructure.toDomainUnauthenticatedUser
import com.uramnoil.serverist.domain.auth.unauthenticated.models.Id
import com.uramnoil.serverist.domain.auth.unauthenticated.models.User
import com.uramnoil.serverist.domain.auth.unauthenticated.repositories.UserRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

class ExposedUserRepository : UserRepository {
    override suspend fun insert(user: User) = runCatching {
        newSuspendedTransaction {
            UnauthenticatedUsers.insert {
                it[id] = user.id.value
                it[email] = user.email.value
                it[hashedPassword] = user.hashedPassword.value
                it[activateCode] = user.activationCode.value
            }
            commit()
        }
    }

    override suspend fun update(user: User): Result<Unit> = runCatching {
        newSuspendedTransaction {
            UnauthenticatedUsers.update({ UnauthenticatedUsers.id eq user.id.value }) {
                it[email] = user.email.value
                it[hashedPassword] = user.hashedPassword.value
                it[activateCode] = user.activationCode.value
            }
            commit()
        }
    }

    override suspend fun delete(user: User) = runCatching {
        newSuspendedTransaction {
            UnauthenticatedUsers.deleteWhere { UnauthenticatedUsers.id eq user.id.value }
            commit()
        }
    }

    override suspend fun findById(id: Id) = runCatching<User?> {
        newSuspendedTransaction {
            UnauthenticatedUsers.select { UnauthenticatedUsers.id eq id.value }.firstOrNull()
        }?.let(ResultRow::toDomainUnauthenticatedUser)?.getOrThrow()
    }
}