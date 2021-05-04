package com.uramnoil.serverist.domain.models.user

class User(
    val id: Id,
    var accountId: AccountId,
    var name: Name,
    var description: Description
)