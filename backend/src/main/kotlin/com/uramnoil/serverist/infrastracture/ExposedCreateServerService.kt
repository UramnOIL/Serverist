package com.uramnoil.serverist.infrastracture

import com.uramnoil.serverist.domain.service.models.server.Id
import com.uramnoil.serverist.domain.service.models.user.User
import com.uramnoil.serverist.domain.service.services.server.CreateServerService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.coroutines.CoroutineContext

class ExposedCreateServerService(private val database: Database, private val context: CoroutineContext) :
    CreateServerService, CoroutineScope by CoroutineScope(context) {
    override fun newAsync(name: String, owner: User, address: String?, port: Int?, description: String): Deferred<Id> =
        async {
            transaction(database) {
                Id(Servers.insertAndGetId {
                    it[Servers.name] = name
                    it[Servers.owner] = owner.id.value
                    it[Servers.address] = address
                    it[Servers.port] = port
                    it[Servers.description] = description
                }.value)
            }
        }
}