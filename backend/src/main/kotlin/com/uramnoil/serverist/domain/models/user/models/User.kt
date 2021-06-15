package com.uramnoil.serverist.domain.models.user.models

import com.uramnoil.serverist.domain.models.kernel.models.HashedPassword
import com.uramnoil.serverist.domain.models.kernel.models.UserId

class User(
    val id: UserId,
    var accountId: AccountId,
    val email: Email,
    var hashedPassword: HashedPassword,
    var name: Name,
    var description: Description
)