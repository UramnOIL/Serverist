package com.uramnoil.serverist.serverist.infrastructure

import com.uramnoil.serverist.domain.common.user.Id
import com.uramnoil.serverist.domain.serverist.models.server.CreatedAt
import com.uramnoil.serverist.domain.serverist.models.server.Description
import com.uramnoil.serverist.domain.serverist.models.server.Host
import com.uramnoil.serverist.domain.serverist.models.server.Name
import com.uramnoil.serverist.domain.serverist.models.server.Port
import com.uramnoil.serverist.domain.serverist.models.user.AccountId
import com.uramnoil.serverist.serverist.application.server.Server
import com.uramnoil.serverist.serverist.application.user.User
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.`java-time`.datetime
import com.uramnoil.serverist.domain.serverist.models.server.Server as DomainServer

object Servers : UUIDTable("servers") {
    val name = varchar("name", 31)
    val ownerId = uuid("owner_id").references(Users.id)
    val createdAt = datetime("created_at")
    val host = char("host", 253).nullable()
    val port = ushort("port").nullable()
    val description = varchar("description", 255)
}

object Users : UUIDTable("users") {
    val accountId = char("account_id", 16).uniqueIndex()
    val name = varchar("name", 32)
    val description = varchar("description", 255)
}


fun ResultRow.toApplicationServer() = Server(
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
    createdAt = CreatedAt(this[Servers.createdAt].toKotlinInstant()),
    ownerId = Id(this[Servers.ownerId]),
    name = Name(this[Servers.name]),
    host = this[Servers.host]?.let { Host(it) },
    port = this[Servers.port]?.let { Port(it) },
    description = Description(this[Servers.description])
)


fun ResultRow.toApplicationUser() = User(
    this[Users.id].value,
    this[Users.accountId],
    this[Users.name],
    this[Users.description]
)

fun ResultRow.toDomainUser() = com.uramnoil.serverist.domain.serverist.models.user.User.new(
    Id(this[Users.id].value),
    AccountId(this[Users.accountId]),
    com.uramnoil.serverist.domain.serverist.models.user.Name(this[Users.name]),
    com.uramnoil.serverist.domain.serverist.models.user.Description(this[Users.description])
)