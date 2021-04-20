package com.uramnoil.serverist.domain.user.models.user

class User(
    val entityId: EntityId,
    var userId: UserId,
    var email: Email,
    var password: HashPassword,
)