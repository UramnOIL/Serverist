package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.domain.models.kernel.models.UserId
import com.uramnoil.serverist.domain.models.server.models.*
import com.uramnoil.serverist.domain.models.server.repositories.ServerRepository
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class ExposedServerRepository : ServerRepository {
    override suspend fun insert(server: Server) {
        TODO("Not yet implemented")
    }

    override suspend fun update(server: Server) = newSuspendedTransaction {
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

    override suspend fun findById(id: Id): Server? {
        return Servers.select { Servers.owner eq id.value }.firstOrNull()?.let {
            Server(
                Id(it[Servers.id].value),
                CreatedAt(it[Servers.createdAt].toKotlinInstance()),
                Name(it[Servers.name]),
                UserId(it[Servers.owner]),
                Address(it[Servers.address]),
                Port(it[Servers.port]),
                Description(it[Servers.description])
            )
        }
    }
}