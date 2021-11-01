package com.uramnoil.serverist.infrastructure.server.domain.repositories

import com.uramnoil.serverist.domain.server.models.Id
import com.uramnoil.serverist.domain.server.models.Server
import com.uramnoil.serverist.domain.server.repositories.ServerRepository
import com.uramnoil.serverist.infrastracture.server.Servers
import com.uramnoil.serverist.infrastracture.server.toDomainServer
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

class ExposedServerRepository : ServerRepository {
    override suspend fun insert(server: Server) = Result.success(Unit) // TODO

    override suspend fun update(server: Server) = kotlin.runCatching {
        newSuspendedTransaction {
            Servers.update({ Servers.id eq server.id.value }) {
                it[name] = server.name.value
                it[address] = server.address.value
                it[port] = server.port.value
                it[description] = server.description.value
            }
            commit()
        }
    }

    override suspend fun delete(server: Server) = kotlin.runCatching {
        newSuspendedTransaction {
            Servers.deleteWhere { Servers.id eq server.id.value }
            commit()
        }
    }

    override suspend fun findById(id: Id): Result<Server?> = kotlin.runCatching {
        newSuspendedTransaction {
            val row = Servers.select { Servers.id eq id.value }.firstOrNull()
            row?.toDomainServer()
        }
    }
}