package com.uramnoil.serverist.domain.unauthenticateduser.models

import com.uramnoil.serverist.domain.kernel.models.user.AccountId
import com.uramnoil.serverist.domain.kernel.models.user.Email
import com.uramnoil.serverist.domain.kernel.models.user.HashedPassword

class UnauthenticatedUser(
    val id: Id,
    val accountId: AccountId,
    val email: Email,
    val hashedPassword: HashedPassword
)