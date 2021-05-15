package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.infrastracture.user.Users
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.`java-time`.datetime

object Servers : UUIDTable("Servers") {
    val name = varchar("name", 16)
    val owner = uuid("owner").references(Users.id)
    val createdAt = datetime("created_at")
    val address = varchar("address", 253).nullable()
    val port = integer("port").nullable()
    val description = varchar("description", 255)
}