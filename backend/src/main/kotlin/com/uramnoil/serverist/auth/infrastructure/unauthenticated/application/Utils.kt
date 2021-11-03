package com.uramnoil.serverist.auth.infrastructure.unauthenticated.application

import com.uramnoil.serverist.auth.infrastructure.unauthenticated.Users
import com.uramnoil.serverist.domain.auth.kernel.model.Email
import com.uramnoil.serverist.domain.auth.kernel.model.HashedPassword
import com.uramnoil.serverist.domain.auth.unauthenticated.models.ExpiredAt
import com.uramnoil.serverist.domain.auth.unauthenticated.models.Id
import com.uramnoil.serverist.server.infrastructure.toKotlinInstant
import org.jetbrains.exposed.sql.ResultRow
import com.uramnoil.serverist.auth.application.unauthenticated.queries.User as ApplicationUser
import com.uramnoil.serverist.domain.auth.unauthenticated.models.User as DomainUser

fun DomainUser.toApplication() = ApplicationUser(
    id = id.value,
    email = email.value,
    hashedPassword = hashedPassword.value,
    expiredAt = expiredAt.value
)

fun ResultRow.toApplicationUnauthenticatedUser() = ApplicationUser(
    this[Users.id].value,
    this[Users.email],
    this[Users.hashedPassword],
    this[Users.expiredAt].toKotlinInstant()
)

fun ResultRow.toDomainUnauthenticatedUser(): Result<DomainUser> = DomainUser.new(
    Id(this[Users.id].value),
    Email(this[Users.email]),
    HashedPassword(this[Users.hashedPassword]),
    ExpiredAt(this[Users.expiredAt].toKotlinInstant())
)