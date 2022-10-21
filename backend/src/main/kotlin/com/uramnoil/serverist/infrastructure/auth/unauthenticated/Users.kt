package com.uramnoil.serverist.infrastracture.auth.unauthenticated

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.`java-time`.datetime

object Users : UUIDTable("users") {
    val accountId = char("account_id", 16).uniqueIndex()
    val email = char("email", 255).uniqueIndex()
    val hashedPassword = char("hashed_password", 255)
    val expiredAt = datetime("expired_datetime")
}