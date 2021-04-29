package com.uramnoil.serverist.domain.models.user

import com.uramnoil.serverist.domain.models.kernel.user.Id

class User(
    val id: Id,
    var accountId: AccountId,
    var name: Name,
    var description: Description
)