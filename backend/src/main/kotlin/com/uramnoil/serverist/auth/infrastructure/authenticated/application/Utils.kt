package com.uramnoil.serverist.auth.infrastructure.authenticated.application

import com.uramnoil.serverist.auth.infrastructure.authenticated.Users
import com.uramnoil.serverist.domain.auth.authenticated.models.User
import com.uramnoil.serverist.domain.auth.kernel.model.Email
import com.uramnoil.serverist.domain.auth.kernel.model.HashedPassword
import org.jetbrains.exposed.sql.ResultRow

fun User.toApplication() = com.uramnoil.serverist.auth.application.authenticated.queries.User(
    id = id.value,
    email = email.value,
    hashedPassword = hashedPassword.value,
)

fun ResultRow.toApplicationAuthenticatedUser() = com.uramnoil.serverist.auth.application.authenticated.queries.User(
    this[Users.id].value,
    this[Users.email],
    this[Users.hashedPassword],
)

fun ResultRow.toDomainAuthenticatedUser(): Result<User> = User.new(
    com.uramnoil.serverist.domain.common.user.Id(this[Users.id].value),
    Email(this[Users.email]),
    HashedPassword(this[Users.hashedPassword]),
)