package com.uramnoil.serverist.domain.models.user

import com.uramnoil.serverist.domain.models.kernel.UserId

class User(
    val id: UserId,
    var accountId: AccountId,
    val email: Email,
    var hashedPassword: HashedPassword,
    var name: Name,
    var description: Description
)