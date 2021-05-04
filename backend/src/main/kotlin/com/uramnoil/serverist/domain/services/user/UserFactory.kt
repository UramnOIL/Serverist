package com.uramnoil.serverist.domain.services.user

import com.uramnoil.serverist.domain.models.user.*
import java.util.*

object UserFactory {
    fun create(
        id: UUID,
        accountId: String,
        email: String,
        hashedPassword: String,
        name: String,
        description: String
    ) =
        User(
            Id(id),
            AccountId(accountId),
            Email(email),
            HashedPassword(hashedPassword),
            Name(name),
            Description(description)
        )
}