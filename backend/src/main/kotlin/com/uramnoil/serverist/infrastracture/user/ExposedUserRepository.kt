package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.domain.models.kernel.models.UserId
import com.uramnoil.serverist.domain.models.user.models.User
import com.uramnoil.serverist.domain.models.user.repositories.UserRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedUserRepository : UserRepository {
    override suspend fun insert(user: User) {
        newSuspendedTransaction {
            Users.insert {
                it[Users.id] = user.id.value
                it[Users.accountId] = user.accountId.value
                it[Users.email] = user.email.value
                it[Users.hashedPassword] = user.hashedPassword.value
                it[Users.name] = user.name.value
                it[Users.description] = user.description.value
            }
            commit()
        }
    }

    override suspend fun update(user: User) {
        newSuspendedTransaction {
            Users.update({ Users.id eq user.id.value }) {
                it[name] = user.name.value
                it[Users.accountId] = user.accountId.value
                it[Users.email] = user.email.value
                it[Users.hashedPassword] = user.hashedPassword.value
                it[Users.name] = user.name.value
                it[Users.description] = user.description.value
                commit()
            }
        }
    }

    override suspend fun delete(user: User) {
        newSuspendedTransaction {
            Users.deleteWhere { Users.id eq user.id.value }
            commit()
        }
    }

    override suspend fun findById(id: UserId): User? {
        return newSuspendedTransaction {
            Users.select { Users.id eq id.value }.firstOrNull()
        }?.let(ResultRow::toDomainUser)
    }
}
