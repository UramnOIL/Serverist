package com.uramnoil.serverist.infrastracture.unauthenticateduser

import com.uramnoil.serverist.domain.kernel.models.user.AccountId
import com.uramnoil.serverist.domain.kernel.models.user.Email
import com.uramnoil.serverist.domain.kernel.models.user.HashedPassword
import com.uramnoil.serverist.domain.unauthenticateduser.models.Id
import com.uramnoil.serverist.infrastracture.user.Users
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.`java-time`.datetime
import com.uramnoil.serverist.application.unauthenticateduser.UnauthenticatedUser as DomainUnauthenticatedUser
import com.uramnoil.serverist.domain.unauthenticateduser.models.UnauthenticatedUser as ApplicationUnauthenticatedUser

object UnauthenticatedUsers : UUIDTable("users") {
    val accountId = varchar("account_id", 16).uniqueIndex()
    val email = varchar("email", 255).uniqueIndex()
    val hashedPassword = varchar("hashed_password", 255)
    val expiredDateTime = datetime("expired_datetime")
}

fun ResultRow.toApplicationUnauthenticatedUser() = DomainUnauthenticatedUser(
    this[Users.id].value,
    this[Users.accountId],
    this[Users.name],
)

fun ResultRow.toDomainUnauthenticatedUser() = ApplicationUnauthenticatedUser(
    Id(this[Users.id].value),
    AccountId(this[Users.accountId]),
    Email(this[Users.email]),
    HashedPassword(this[Users.hashedPassword])
)