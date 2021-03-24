package com.uramnoil.serverist.infrastracture

import org.jetbrains.exposed.dao.id.UUIDTable

object Servers : UUIDTable("Servers") {
    val name = text("name")
    val owner = uuid("owner").references(Users.id)
    val address = text("address").nullable()
    val port = integer("port").nullable()
    val description = text("description")
}