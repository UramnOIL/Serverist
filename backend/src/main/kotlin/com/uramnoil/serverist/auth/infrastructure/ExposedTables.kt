package com.uramnoil.serverist.auth.infrastructure

import com.uramnoil.serverist.auth.infrastructure.application.AuthenticatedUser
import com.uramnoil.serverist.auth.infrastructure.application.UnauthenticatedUser
import com.uramnoil.serverist.domain.auth.kernel.model.Email
import com.uramnoil.serverist.domain.auth.kernel.model.HashedPassword
import com.uramnoil.serverist.domain.auth.unauthenticated.models.ActivationCode
import com.uramnoil.serverist.domain.auth.unauthenticated.models.Id
import com.uramnoil.serverist.domain.auth.unauthenticated.models.User
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow

object UnauthenticatedUsers : UUIDTable("unauthenticated_users") {
    val email = char("email", 255).uniqueIndex()
    val hashedPassword = char("hashed_password", 255)
    val activateCode = uuid("activate_code")
}

object AuthenticatedUsers : UUIDTable("authenticated_users") {
    val email = char("email", 255).uniqueIndex()
    val hashedPassword = char("hashed_password", 255)
}


fun ResultRow.toApplicationUnauthenticatedUser() = UnauthenticatedUser(
    this[UnauthenticatedUsers.id].value,
    this[UnauthenticatedUsers.email],
    this[UnauthenticatedUsers.hashedPassword],
    this[UnauthenticatedUsers.activateCode]
)

fun ResultRow.toDomainUnauthenticatedUser(): Result<User> = User.new(
    Id(this[UnauthenticatedUsers.id].value),
    Email(this[UnauthenticatedUsers.email]),
    HashedPassword(this[UnauthenticatedUsers.hashedPassword]),
    ActivationCode(this[UnauthenticatedUsers.activateCode])
)


fun ResultRow.toApplicationAuthenticatedUser() = AuthenticatedUser(
    this[AuthenticatedUsers.id].value,
    this[AuthenticatedUsers.email],
    this[AuthenticatedUsers.hashedPassword],
)

fun ResultRow.toDomainAuthenticatedUser(): Result<com.uramnoil.serverist.domain.auth.authenticated.models.User> =
    com.uramnoil.serverist.domain.auth.authenticated.models.User.new(
        com.uramnoil.serverist.domain.common.user.Id(this[AuthenticatedUsers.id].value),
        Email(this[AuthenticatedUsers.email]),
        HashedPassword(this[AuthenticatedUsers.hashedPassword]),
    )