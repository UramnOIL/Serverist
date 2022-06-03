package com.uramnoil.serverist.serverist.infrastructures.application.usecase.server

import com.uramnoil.serverist.serverist.application.usecases.server.queries.ServerDto
import com.uramnoil.serverist.common.domain.models.kernel.UserId
import com.uramnoil.serverist.serverist.domain.models.server.*
import com.uramnoil.serverist.serverist.infrastructures.application.Servers
import kotlinx.datetime.toKotlinInstant
import org.jetbrains.exposed.sql.ResultRow
import java.time.LocalDateTime
import java.time.ZoneOffset
import com.uramnoil.serverist.serverist.domain.models.server.Server as DomainServer

fun ResultRow.toApplicationServer() = ServerDto(
    this[Servers.id].value,
    this[Servers.createdAt].toKotlinInstant(),
    this[Servers.ownerId],
    this[Servers.name],
    this[Servers.host],
    this[Servers.port]?.toInt(),
    this[Servers.description]
)

fun ResultRow.toDomainServer() = DomainServer(
    id = Id(this[Servers.id].value),
    createdAt = DateTime(this[Servers.createdAt].toKotlinInstant()),
    ownerId = UserId(this[Servers.ownerId]),
    name = Name(this[Servers.name]),
    host = this[Servers.host]?.let { Host(it) },
    port = this[Servers.port]?.let { Port(it) },
    description = Description(this[Servers.description])
)

fun LocalDateTime.toKotlinInstant() = toInstant(ZoneOffset.UTC).toKotlinInstant()
