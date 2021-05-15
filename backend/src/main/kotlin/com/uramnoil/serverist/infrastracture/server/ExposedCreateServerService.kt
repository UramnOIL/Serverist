package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.domain.models.server.*
import com.uramnoil.serverist.domain.models.user.User
import com.uramnoil.serverist.domain.services.server.CreateServerService
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class ExposedCreateServerService(private val database: Database) :
    CreateServerService {
    override suspend fun new(
        name: Name,
        owner: User,
        address: Address,
        port: Port,
        description: Description
    ): Id = newSuspendedTransaction(db = database) {
        val id = Id(Servers.insertAndGetId {
            it[createdAt] = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC)
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