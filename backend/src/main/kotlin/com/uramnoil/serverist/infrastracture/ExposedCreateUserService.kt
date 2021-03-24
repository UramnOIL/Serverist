package com.uramnoil.serverist.infrastracture

import com.uramnoil.serverist.domain.service.models.user.Id
import com.uramnoil.serverist.domain.service.services.user.CreateUserService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.coroutines.CoroutineContext

class ExposedCreateUserService(private val database: Database, private val context: CoroutineContext) :
    CreateUserService, CoroutineScope by CoroutineScope(context) {
    override fun newAsync(name: String, description: String): Deferred<Id> = async {
        transaction(database) {
            val id = Id(Users.insertAndGetId {
                it[Users.name] = name
                it[Users.description] = description
            }.value)
            commit()
            id
        }
    }
}