package com.uramnoil.serverist.serverist.infrastructure

import com.uramnoil.serverist.domain.common.user.Id
import com.uramnoil.serverist.domain.serverist.models.server.*
import com.uramnoil.serverist.serverist.application.server.Server
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime
import java.time.ZoneOffset
import com.uramnoil.serverist.domain.serverist.models.server.Server as DomainServer

object Servers : UUIDTable("servers") {
    val name = varchar("name", 31)
    val owner = uuid("owner").references(Users.id)
    val createdAt = datetime("created_at")
    val host = char("host", 253).nullable()
    val port = ushort("port").nullable()
    val description = varchar("description", 255)
}

fun ResultRow.toApplicationServer() = Server(
    this[Servers.id].value,
    this[Servers.createdAt].toKotlinInstant(),
    this[Servers.owner],
    this[Servers.name],
    this[Servers.host],
    this[Servers.port],
    this[Servers.description]
)

fun ResultRow.toDomainServer() = DomainServer(
    id = Id(this[Servers.id].value),
    createdAt = CreatedAt(this[Servers.createdAt].toKotlinInstant()),
    ownerId = Id(this[Servers.owner]),
    name = Name(this[Servers.name]),
    host = this[Servers.host]?.let { Host(it) },
    port = this[Servers.port]?.let { Port(it) },
    description = Description(this[Servers.description])
)

fun LocalDateTime.toKotlinInstant() = toInstant(ZoneOffset.UTC).toKotlinInstant()

fun Instant.toJavaLocalDataTime() = LocalDateTime.ofInstant(toJavaInstant(), ZoneOffset.UTC)