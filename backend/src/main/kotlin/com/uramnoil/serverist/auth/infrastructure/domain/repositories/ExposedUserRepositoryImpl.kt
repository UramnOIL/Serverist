package com.uramnoil.serverist.auth.infrastructure.domain.repositories

import com.uramnoil.serverist.auth.infrastructure.AuthenticatedUsers
import com.uramnoil.serverist.auth.infrastructure.toDomainAuthenticatedUser
import com.uramnoil.serverist.domain.auth.authenticated.models.User
import com.uramnoil.serverist.domain.auth.authenticated.repositories.UserRepository
import com.uramnoil.serverist.domain.common.user.Id
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedUserRepositoryImpl : UserRepository {
    override suspend fun insert(user: User): Result<Unit> = kotlin.runCatching {
        val row = newSuspendedTransaction {
            AuthenticatedUsers.insert {
                it[id] = user.id.value
                it[email] = user.email.value
                it[hashedPassword] = user.hashedPassword.value
            }
            commit()
        }
    }

    override suspend fun update(user: User): Result<Unit> = kotlin.runCatching {
        newSuspendedTransaction {
            AuthenticatedUsers.update({ AuthenticatedUsers.id eq user.id.value }) {
                it[email] = user.email.value
                it[hashedPassword] = user.hashedPassword.value
            }
            commit()
        }
    }

    override suspend fun delete(user: User): Result<Unit> = runCatching {
        newSuspendedTransaction {
            AuthenticatedUsers.deleteWhere { AuthenticatedUsers.id eq user.id.value }
            commit()
        }
    }

    override suspend fun findById(id: Id): Result<User?> = runCatching {
        newSuspendedTransaction {
            AuthenticatedUsers.select { AuthenticatedUsers.id eq id.value }.firstOrNull()
        }?.let(ResultRow::toDomainAuthenticatedUser)?.getOrThrow()
    }
}