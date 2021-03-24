package com.uramnoil.serverist.infrastracture

import com.uramnoil.serverist.domain.service.models.server.Id
import com.uramnoil.serverist.domain.service.models.server.Server
import com.uramnoil.serverist.domain.service.repositories.ServerRepository
import com.uramnoil.serverist.domain.service.services.server.ServerFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import kotlin.coroutines.CoroutineContext

class ExposedServerRepository(private val database: Database, private val context: CoroutineContext) : ServerRepository,
    CoroutineScope by CoroutineScope(context) {
    override fun findByIdAsync(id: Id): Deferred<Server?> = async {
        transaction(database) {
            val result = Servers.select { Servers.id eq id.value }.firstOrNull() ?: return@transaction null

            result.let {
                ServerFactory.create(
                    it[Servers.id].value,
                    it[Servers.name],
                    it[Servers.owner],
                    it[Servers.address],
                    it[Servers.port],
                    it[Servers.description]
                )
            }
        }
    }

    override fun storeAsync(server: Server): Deferred<Unit> = async {
        transaction {
            Servers.update({ Servers.id eq server.id.value }) {
                it[name] = server.name.value
                it[address] = server.address.value
                it[port] = server.port.value
                it[description] = server.description.value
            }
        }
    }

    override fun deleteAsync(server: Server): Deferred<Unit> = async {
        transaction {
            Servers.deleteWhere { Servers.id eq server.id.value }
        }
    }
}