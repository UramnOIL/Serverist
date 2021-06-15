package com.uramnoil.serverist.domain.user.models

class User(
    val id: com.uramnoil.serverist.domain.kernel.models.UserId,
    var accountId: AccountId,
    val email: Email,
    var hashedPassword: com.uramnoil.serverist.domain.kernel.models.HashedPassword,
    var name: Name,
    var description: Description
)