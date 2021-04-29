package com.uramnoil.serverist.infrastracture.server

import com.uramnoil.serverist.infrastracture.user.Users
import org.jetbrains.exposed.dao.id.UUIDTable

object Servers : UUIDTable("Servers") {
    val name = varchar("name", 16)
    val owner = uuid("owner").references(Users.id)
    val address = varchar("address", 253).nullable()
    val port = integer("port").nullable()
    val description = text("description")
}