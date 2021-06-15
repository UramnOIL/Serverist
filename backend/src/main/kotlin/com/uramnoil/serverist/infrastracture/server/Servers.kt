package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.application.server.Server
import com.uramnoil.serverist.domain.models.kernel.UserId
import com.uramnoil.serverist.domain.models.server.*
import com.uramnoil.serverist.infrastracture.user.Users
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime
import java.time.ZoneOffset
import com.uramnoil.serverist.domain.models.server.Server as DomainServer

object Servers : UUIDTable("servers") {
    val name = varchar("name", 16)
    val owner = uuid("owner").references(Users.id)
    val createdAt = datetime("created_at")
    val address = varchar("address", 253).nullable()
    val port = integer("port").nullable()
    val description = varchar("description", 255)
}

fun ResultRow.toApplicationServer(): Server = Server(
    this[Servers.id].value,
    this[Servers.createdAt].toKotlinInstance(),
    this[Servers.owner],
    this[Servers.name],
    this[Servers.address],
    this[Servers.port],
    this[Servers.description]
)

fun ResultRow.toDomainServer() = DomainServer(
    id = Id(this[Servers.id].value),
    createdAt = CreatedAt(this[Servers.createdAt].toKotlinInstance()),
    ownerId = UserId(this[Servers.owner]),
    name = Name(this[Servers.name]),
    address = Address(this[Servers.address]),
    port = Port(this[Servers.port]),
    description = Description(this[Servers.description])
)

fun LocalDateTime.toKotlinInstance() = toInstant(ZoneOffset.UTC).toKotlinInstant()

fun Instant.toJavaLocalDataTime() = LocalDateTime.ofInstant(toJavaInstant(), ZoneOffset.UTC)