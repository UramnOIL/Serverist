package com.uramnoil.serverist.domain.services.user

import com.uramnoil.serverist.domain.models.user.*
import java.util.*

object UserFactory {
    fun create(
        id: String,
        accountId: String,
        email: String,
        hashedPassword: ByteArray,
        name: String,
        description: String
    ) =
        User(
            Id(UUID.fromString(id)),
            AccountId(accountId),
            Email(email),
            HashedPassword(hashedPassword),
            Name(name),
            Description(description)
        )
}