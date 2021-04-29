package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.domain.models.server.Id
import com.uramnoil.serverist.domain.models.server.Server
import com.uramnoil.serverist.domain.repositories.ServerRepository
import com.uramnoil.serverist.domain.services.server.ServerFactory
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class ExposedServerRepository(private val database: Database) : ServerRepository {
    override suspend fun findById(id: Id): Server? = newSuspendedTransaction(db = database) {
        val result = Servers.select { Servers.id eq id.value }.firstOrNull() ?: return@newSuspendedTransaction null

        result.let {
            ServerFactory.create(
                it[Servers.id].value.toString(),
                it[Servers.name],
                it[Servers.owner].toString(),
                it[Servers.address],
                it[Servers.port],
                it[Servers.description]
            )
        }
    }

    override suspend fun store(server: Server) = newSuspendedTransaction(db = database) {
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