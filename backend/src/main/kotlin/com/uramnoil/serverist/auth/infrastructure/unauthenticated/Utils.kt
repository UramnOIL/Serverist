package com.uramnoil.serverist.auth.infrastructure.unauthenticated.application

import com.uramnoil.serverist.auth.infrastructure.unauthenticated.Users
import com.uramnoil.serverist.domain.auth.kernel.model.Email
import com.uramnoil.serverist.domain.auth.kernel.model.HashedPassword
import com.uramnoil.serverist.domain.auth.unauthenticated.models.ActivationCode
import com.uramnoil.serverist.domain.auth.unauthenticated.models.Id
import org.jetbrains.exposed.sql.ResultRow
import com.uramnoil.serverist.application.unauthenticated.queries.User as ApplicationUser
import com.uramnoil.serverist.domain.auth.unauthenticated.models.User as DomainUser

fun DomainUser.toApplication() = ApplicationUser(
    id = id.value,
    email = email.value,
    hashedPassword = hashedPassword.value,
    activationCode = activationCode.value
)

fun ResultRow.toApplicationUnauthenticatedUser() = ApplicationUser(
    this[Users.id].value,
    this[Users.email],
    this[Users.hashedPassword],
    this[Users.activateCode]
)

fun ResultRow.toDomainUnauthenticatedUser(): Result<DomainUser> = DomainUser.new(
    Id(this[Users.id].value),
    Email(this[Users.email]),
    HashedPassword(this[Users.hashedPassword]),
    ActivationCode(this[Users.activateCode])
)