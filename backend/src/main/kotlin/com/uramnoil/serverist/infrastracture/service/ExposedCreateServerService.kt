package com.uramnoil.serverist.infrastracture.service

import com.uramnoil.serverist.domain.models.server.*
import com.uramnoil.serverist.domain.models.user.User
import com.uramnoil.serverist.domain.services.server.CreateServerService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.coroutines.CoroutineContext

class ExposedCreateServerService(private val database: Database, context: CoroutineContext) :
    CreateServerService, CoroutineScope by CoroutineScope(context) {
    override fun newAsync(
        name: Name,
        owner: User,
        address: Address,
        port: Port,
        description: Description
    ): Deferred<Id> = async {
        transaction(database) {
            val id = Id(Servers.insertAndGetId {
                it[Servers.name] = name.value
                it[Servers.owner] = owner.id.value
                it[Servers.address] = address.value
                it[Servers.port] = port.value
                it[Servers.description] = description.value
            }.value)
            commit()
            id
        }
    }
}