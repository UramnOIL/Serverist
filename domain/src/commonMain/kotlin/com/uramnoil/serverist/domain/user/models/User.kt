package com.uramnoil.serverist.domain.user.models

import com.uramnoil.serverist.domain.kernel.models.user.AccountId
import com.uramnoil.serverist.domain.kernel.models.user.Email
import com.uramnoil.serverist.domain.kernel.models.user.HashedPassword
import com.uramnoil.serverist.domain.kernel.models.user.UserId

class User(
    val id: UserId,
    var accountId: AccountId,
    val email: Email,
    var hashedPassword: HashedPassword,
    var name: Name,
    var description: Description
)