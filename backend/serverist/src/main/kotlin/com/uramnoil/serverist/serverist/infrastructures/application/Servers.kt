package com.uramnoil.serverist.serverist.infrastructures.application

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.`java-time`.datetime

object Servers : UUIDTable("servers") {
    val name = varchar("name", 31)
    val ownerId = uuid("owner_id").references(Users.id)
    val createdAt = datetime("created_at")
    val host = char("host", 253).nullable()
    @OptIn(ExperimentalUnsignedTypes::class)
    val port = ushort("port").nullable()
    val description = varchar("description", 255)
}