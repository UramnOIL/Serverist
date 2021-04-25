package com.uramnoil.serverist.domain.models.user

class User(
    val id: Id,
    var accountId: AccountId,
    var email: Email,
    var hashedPassword: HashedPassword,
    var name: Name,
    var description: Description
)