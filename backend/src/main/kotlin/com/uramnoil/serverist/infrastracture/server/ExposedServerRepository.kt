package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.domain.models.server.Id
import com.uramnoil.serverist.domain.models.server.Server
import com.uramnoil.serverist.domain.repositories.ServerRepository
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class ExposedServerRepository : ServerRepository {
    override suspend fun findById(id: Id): Server? = newSuspendedTransaction {
        val result = Servers.select { Servers.id eq id.value }.firstOrNull() ?: return@newSuspendedTransaction null

        ExposedServerFactory.create(result)
    }

    override suspend fun store(server: Server) = newSuspendedTransaction {
        Servers.update({ Servers.id eq server.id.value }) {
            it[name] = server.name.value
            it[address] = server.address.value
            it[port] = server.port.value
            it[description] = server.description.value
        }
        commit()
    }

    override suspend fun delete(server: Server) {
        transaction {
            Servers.deleteWhere { Servers.id eq server.id.value }
            commit()
        }
    }
}