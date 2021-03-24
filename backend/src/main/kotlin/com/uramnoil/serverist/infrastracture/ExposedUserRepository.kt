package com.uramnoil.serverist.infrastracture

import com.uramnoil.serverist.domain.service.models.user.Id
import com.uramnoil.serverist.domain.service.models.user.User
import com.uramnoil.serverist.domain.service.repositories.UserRepository
import com.uramnoil.serverist.domain.service.services.user.UserFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*
import kotlin.coroutines.CoroutineContext

class ExposedUserRepository(private val database: Database, private val context: CoroutineContext) : UserRepository,
    CoroutineScope by CoroutineScope(context) {
    private val logger = StdOutSqlLogger

    override fun findByIdAsync(id: Id): Deferred<User?> = async {
        transaction(database) {
            addLogger(logger)
            SchemaUtils.create(Users)

            val query = Users.select { Users.id eq id.value }.firstOrNull() ?: return@transaction null

            query.let {
                UserFactory.create(
                    it[Users.id].value,
                    it[Users.name],
                    it[Users.description]
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
