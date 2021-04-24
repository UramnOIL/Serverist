package com.uramnoil.serverist.infrastracture.login

import com.uramnoil.serverist.domain.models.user.*
import com.uramnoil.serverist.domain.services.user.CreateUserService
import com.uramnoil.serverist.infrastracture.user.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.coroutines.CoroutineContext

class ExposedCreateUserService(private val database: Database, val context: CoroutineContext) :
    CreateUserService, CoroutineScope by CoroutineScope(context) {
    override fun newAsync(
        accountId: AccountId,
        email: Email,
        password: HashedPassword,
        name: Name,
        description: Description
    ): Deferred<Id> = async {
        transaction(database) {
            val id = Id(Users.insertAndGetId {
                it[Users.name] = Users.name
                it[Users.description] = Users.description
            }.value)
            commit()
            id
        }
    }
}