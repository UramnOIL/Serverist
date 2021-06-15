package com.uramnoil.serverist.infrastracture.user

import org.jetbrains.exposed.dao.id.UUIDTable

object Users : UUIDTable("users") {
    val accountId = varchar("account_id", 16).uniqueIndex()
    val email = varchar("email", 255).uniqueIndex()
    val hashedPassword = varchar("hashed_password", 255)
    val name = varchar("name", 32)
    val description = varchar("description", 255)
}