package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.domain.kernel.models.user.UserId
import com.uramnoil.serverist.domain.user.models.User
import com.uramnoil.serverist.domain.user.repositories.UserRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedUserRepository : UserRepository {
    override suspend fun insert(user: User) = kotlin.runCatching {
        newSuspendedTransaction {
            Users.insert {
                it[id] = user.id.value
                it[accountId] = user.accountId.value
                it[email] = user.email.value
                it[hashedPassword] = user.hashedPassword.value
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
                it[email] = user.email.value
                it[hashedPassword] = user.hashedPassword.value
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

    override suspend fun findById(id: UserId) = kotlin.runCatching {
        newSuspendedTransaction {
            Users.select { Users.id eq id.value }.firstOrNull()
        }?.let(ResultRow::toDomainUser)
    }
}
