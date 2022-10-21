package com.uramnoil.serverist.infrastructure.auth.authenticated

import com.uramnoil.serverist.domain.auth.kernel.model.Email
import com.uramnoil.serverist.domain.auth.kernel.model.HashedPassword
import com.uramnoil.serverist.domain.auth.unauthenticated.models.ExpiredAt
import com.uramnoil.serverist.domain.auth.unauthenticated.models.Id
import com.uramnoil.serverist.domain.auth.authenticated.models.User
import com.uramnoil.serverist.domain.common.user.UserId
import com.uramnoil.serverist.infrastracture.auth.unauthenticated.Users
import com.uramnoil.serverist.infrastracture.server.toKotlinInstant
import org.jetbrains.exposed.sql.ResultRow

fun User.toApplication() = com.uramnoil.serverist.application.auth.authenticated.queries.User(
    id = id.value,
    email = email.value,
    hashedPassword = hashedPassword.value,
)

fun ResultRow.toApplicationAuthenticatedUser() = com.uramnoil.serverist.application.auth.authenticated.queries.User(
    this[Users.id].value,
    this[Users.email],
    this[Users.hashedPassword],
)

fun ResultRow.toDomainAuthenticatedUser(): Result<User> = User.new(
    UserId(this[Users.id].value),
    Email(this[Users.email]),
    HashedPassword(this[Users.hashedPassword]),
)