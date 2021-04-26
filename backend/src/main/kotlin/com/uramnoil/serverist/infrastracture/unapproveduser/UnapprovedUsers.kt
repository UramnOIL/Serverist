package com.uramnoil.serverist.infrastracture.unapproveduser

import org.jetbrains.exposed.dao.id.IdTable

object UnapprovedUsers : IdTable<String>() {
    override val id = varchar("account_id", 15).entityId()
    val email = varchar("email", 255)
    val hashedPassword = varchar("hashed_password", 255)
}