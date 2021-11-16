package com.uramnoil.serverist.auth.infrastructure.authenticated.domain

import com.uramnoil.serverist.auth.infrastructure.authenticated.Users
import com.uramnoil.serverist.auth.infrastructure.authenticated.application.toDomainAuthenticatedUser
import com.uramnoil.serverist.domain.auth.authenticated.models.User
import com.uramnoil.serverist.domain.auth.authenticated.repositories.UserRepository
import com.uramnoil.serverist.domain.common.user.Id
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedUserRepositoryImpl : UserRepository {
    override suspend fun insert(user: User): Result<Unit> = kotlin.runCatching {
        val row = newSuspendedTransaction {
            Users.insert {
                it[id] = user.id.value
                it[email] = user.email.value
                it[hashedPassword] = user.hashedPassword.value
            }
            commit()
        }
    }

    override suspend fun update(user: User): Result<Unit> = kotlin.runCatching {
        newSuspendedTransaction {
            Users.update({ Users.id eq user.id.value }) {
                it[email] = user.email.value
                it[hashedPassword] = user.hashedPassword.value
            }
            commit()
        }
    }

    override suspend fun delete(user: User): Result<Unit> = runCatching {
        newSuspendedTransaction {
            Users.deleteWhere { Users.id eq user.id.value }
            commit()
        }
    }

    override suspend fun findById(id: Id): Result<User?> = runCatching {
        newSuspendedTransaction {
            Users.select { Users.id eq id.value }.firstOrNull()
        }?.let(ResultRow::toDomainAuthenticatedUser)?.getOrThrow()
    }
}