package com.uramnoil.serverist.infrastructure.auth.unauthenticated.domain.repositories

import com.uramnoil.serverist.domain.auth.unauthenticated.models.Id
import com.uramnoil.serverist.domain.auth.unauthenticated.models.User
import com.uramnoil.serverist.domain.auth.unauthenticated.repositories.UserRepository
import com.uramnoil.serverist.infrastracture.auth.unauthenticated.Users
import com.uramnoil.serverist.infrastructure.auth.unauthenticated.toDomainUnauthenticatedUser
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedUserRepository : UserRepository {
    override suspend fun insert(user: User) = runCatching {
        newSuspendedTransaction {
            Users.insert {
                it[id] = user.id.value
                it[email] = user.email.value
                it[hashedPassword] = user.hashedPassword.value
            }
            commit()
        }
    }

    override suspend fun update(user: User): Result<Unit> = runCatching {
        newSuspendedTransaction {
            Users.update({ Users.id eq user.id.value }) {
                it[email] = user.email.value
                it[hashedPassword] = user.hashedPassword.value
                commit()
            }
        }
    }

    override suspend fun delete(user: User) = runCatching {
        newSuspendedTransaction {
            Users.deleteWhere { Users.id eq user.id.value }
            commit()
        }
    }

    override suspend fun findById(id: Id) = runCatching<User?> {
        newSuspendedTransaction {
            Users.select { Users.id eq id.value }.firstOrNull()
        }?.let(ResultRow::toDomainUnauthenticatedUser)?.getOrThrow()
    }
}