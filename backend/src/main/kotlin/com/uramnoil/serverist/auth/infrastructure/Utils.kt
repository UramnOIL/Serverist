package com.uramnoil.serverist.auth.infrastructure

import com.uramnoil.serverist.domain.auth.unauthenticated.models.User
import com.uramnoil.serverist.application.authenticated.queries.User as ApplicationUser
import com.uramnoil.serverist.domain.auth.authenticated.models.User as DomainUser

fun DomainUser.toApplication() = ApplicationUser(
    id = id.value,
    email = email.value,
    hashedPassword = hashedPassword.value,
)

fun User.toApplication() = com.uramnoil.serverist.application.unauthenticated.queries.User(
    id = id.value,
    email = email.value,
    hashedPassword = hashedPassword.value,
    activationCode = activationCode.value
)