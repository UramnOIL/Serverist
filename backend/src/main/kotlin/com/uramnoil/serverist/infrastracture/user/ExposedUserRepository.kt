package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.domain.models.user.Id
import com.uramnoil.serverist.domain.models.user.User
import com.uramnoil.serverist.domain.repositories.UserRepository
import com.uramnoil.serverist.domain.services.user.UserFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.coroutines.CoroutineContext

class ExposedUserRepository(private val database: Database, context: CoroutineContext) : UserRepository,
    CoroutineScope by CoroutineScope(context) {
    private val logger = StdOutSqlLogger

    override fun findByIdAsync(id: Id): Deferred<User?> = async {
        transaction(database) {
            addLogger(logger)
            SchemaUtils.create(Users)

            val query = Users.select { Users.id eq id.value }.firstOrNull() ?: return@transaction null

            query.let {
                UserFactory.create(
                    id = it[Users.id].value,
                    accountId = it[Users.accountId],
                    email = it[Users.email],
                    hashedPassword = it[Users.hashedPassword],
                    name = it[Users.name],
                    description = it[Users.description]
                )
            }
        }
    }

    override fun storeAsync(user: User): Deferred<Unit> = async {
        transaction {
            addLogger(logger)
            SchemaUtils.create(Users)

            Users.update({ Users.id eq user.id.value }) {
                it[name] = user.name.value
                it[description] = user.description.value
            }
            commit()
        }
    }

    override fun deleteAsync(user: User): Deferred<Unit> = async {
        transaction {
            addLogger(logger)
            SchemaUtils.create(Users)

            Users.deleteWhere { Users.id eq user.id.value }
            commit()
        }
    }
}
