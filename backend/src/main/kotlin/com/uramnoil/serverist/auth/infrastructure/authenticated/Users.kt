package com.uramnoil.serverist.auth.infrastructure.authenticated

import org.jetbrains.exposed.dao.id.UUIDTable

object Users : UUIDTable("authenticated_users") {
    val email = char("email", 255).uniqueIndex()
    val hashedPassword = char("hashed_password", 255)
}