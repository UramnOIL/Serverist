package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.domain.user.models.AccountId
import com.uramnoil.serverist.domain.user.models.Description
import com.uramnoil.serverist.domain.user.models.Email
import com.uramnoil.serverist.domain.user.models.Name
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow
import com.uramnoil.serverist.application.user.User as ApplicationUser
import com.uramnoil.serverist.domain.user.models.User as DomainUser

object Users : UUIDTable("users") {
    val accountId = varchar("account_id", 16).uniqueIndex()
    val email = varchar("email", 255).uniqueIndex()
    val hashedPassword = varchar("hashed_password", 255)
    val name = varchar("name", 32)
    val description = varchar("description", 255)
}

fun ResultRow.toApplicationUser() = ApplicationUser(
    this[Users.id].value,
    this[Users.accountId],
    this[Users.name],
    this[Users.description]
)

fun ResultRow.toDomainUser() = DomainUser(
    com.uramnoil.serverist.domain.kernel.models.UserId(this[Users.id].value),
    AccountId(this[Users.accountId]),
    Email(this[Users.email]),
    com.uramnoil.serverist.domain.kernel.models.HashedPassword(this[Users.hashedPassword]),
    Name(this[Users.name]),
    Description(this[Users.description])
)