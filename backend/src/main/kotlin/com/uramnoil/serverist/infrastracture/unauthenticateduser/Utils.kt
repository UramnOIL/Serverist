package com.uramnoil.serverist.infrastracture.unauthenticateduser

import com.uramnoil.serverist.application.unauthenticateduser.UnauthenticatedUser as ApplicationUnauthenticatedUser
import com.uramnoil.serverist.domain.unauthenticateduser.models.UnauthenticatedUser as DomainUnauthenticatedUser

fun DomainUnauthenticatedUser.toApplication() = ApplicationUnauthenticatedUser(
    id = id.value,
    accountId = accountId.value,
    email = email.value
)