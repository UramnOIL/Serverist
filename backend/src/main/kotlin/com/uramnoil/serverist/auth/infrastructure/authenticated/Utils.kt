package com.uramnoil.serverist.auth.infrastructure.authenticated

import com.uramnoil.serverist.domain.auth.kernel.model.Email
import com.uramnoil.serverist.domain.auth.kernel.model.HashedPassword
import org.jetbrains.exposed.sql.ResultRow
import com.uramnoil.serverist.application.authenticated.queries.User as ApplicationUser
import com.uramnoil.serverist.domain.auth.authenticated.models.User as DomainUser

fun DomainUser.toApplication() = ApplicationUser(
    id = id.value,
    email = email.value,
    hashedPassword = hashedPassword.value,
)

fun ResultRow.toApplicationAuthenticatedUser() = ApplicationUser(
    this[Users.id].value,
    this[Users.email],
    this[Users.hashedPassword],
)

fun ResultRow.toDomainAuthenticatedUser(): Result<DomainUser> = DomainUser.new(
    com.uramnoil.serverist.domain.common.user.Id(this[Users.id].value),
    Email(this[Users.email]),
    HashedPassword(this[Users.hashedPassword]),
)