package com.uramnoil.serverist.infrastracture

import org.jetbrains.exposed.dao.id.UUIDTable

object Users : UUIDTable() {
    val name = text("name")
    val description = text("description")
}