package com.uramnoil.serverist.auth.infrastructure.unauthenticated

import org.jetbrains.exposed.dao.id.UUIDTable

object Users : UUIDTable("users") {
    val accountId = char("account_id", 16).uniqueIndex()
    val email = char("email", 255).uniqueIndex()
    val hashedPassword = char("hashed_password", 255)
    val activateCode = char("activate_code", 31)
}