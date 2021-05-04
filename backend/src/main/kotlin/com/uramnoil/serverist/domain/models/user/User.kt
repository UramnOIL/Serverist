package com.uramnoil.serverist.domain.models.user

class User(
    val id: Id,
    val email: Email,
    var accountId: AccountId,
    var hashedPassword: HashedPassword,
    var name: Name,
    var description: Description
)