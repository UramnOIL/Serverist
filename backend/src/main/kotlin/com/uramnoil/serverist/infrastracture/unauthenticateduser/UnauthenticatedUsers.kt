package com.uramnoil.serverist.infrastracture.unauthenticateduser

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.`java-time`.datetime

object UnauthenticatedUsers : UUIDTable("users") {
    val accountId = varchar("account_id", 16).uniqueIndex()
    val email = varchar("email", 255).uniqueIndex()
    val hashedPassword = varchar("hashed_password", 255)
    val expiredDateTime = datetime("expired_datetime")
}