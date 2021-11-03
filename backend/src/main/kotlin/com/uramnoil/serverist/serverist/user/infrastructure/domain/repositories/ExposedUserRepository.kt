package com.uramnoil.serverist.infrastracture.user.repositories

import com.uramnoil.serverist.domain.common.user.Id
import com.uramnoil.serverist.domain.user.models.User
import com.uramnoil.serverist.domain.user.repositories.UserRepository
import com.uramnoil.serverist.serverist.user.infrastructure.Users
import com.uramnoil.serverist.serverist.user.infrastructure.application.toDomainUser
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

class ExposedUserRepository : UserRepository {
    override suspend fun insert(user: User) = kotlin.runCatching {
        newSuspendedTransaction {
            Users.insert {
                it[id] = user.id.value
                it[accountId] = user.accountId.value
                it[name] = user.name.value
                it[description] = user.description.value
            }
            commit()
        }
    }

    override suspend fun update(user: User): Result<Unit> = kotlin.runCatching {
        newSuspendedTransaction {
            Users.update({ Users.id eq user.id.value }) {
                it[name] = user.name.value
                it[accountId] = user.accountId.value
                it[name] = user.name.value
                it[description] = user.description.value
                commit()
            }
        }
    }

    override suspend fun delete(user: User) = kotlin.runCatching {
        newSuspendedTransaction {
            Users.deleteWhere { Users.id eq user.id.value }
            commit()
        }
    }

    override suspend fun findById(id: Id): Result<User?> = runCatching {
        newSuspendedTransaction {
            Users.select { Users.id eq id.value }.firstOrNull()
        }?.toDomainUser()?.getOrThrow()
    }
}
