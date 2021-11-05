package com.uramnoil.serverist.serverist.user.infrastructure

import org.jetbrains.exposed.dao.id.UUIDTable

object Users : UUIDTable("users") {
    val accountId = char("account_id", 16).uniqueIndex()
    val name = varchar("name", 32)
    val description = varchar("description", 255)
}