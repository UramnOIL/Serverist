package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.domain.kernel.models.user.UserId
import com.uramnoil.serverist.domain.server.models.*
import com.uramnoil.serverist.domain.server.repositories.ServerRepository
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

    override suspend fun findById(id: Id) = kotlin.runCatching {
        newSuspendedTransaction {
            Servers.select { Servers.owner eq id.value }.firstOrNull()?.let {
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
}