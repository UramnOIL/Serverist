package com.uramnoil.serverist.domain.unauthenticateduser.models

class UnauthenticatedUser(
    val id: Id,
    val accountId: AccountId,
    val email: Email,
    val hashedPassword: com.uramnoil.serverist.domain.kernel.models.HashedPassword
)