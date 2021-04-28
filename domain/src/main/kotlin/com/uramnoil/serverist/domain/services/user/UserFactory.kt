package com.uramnoil.serverist.domain.services.user

import com.uramnoil.serverist.domain.models.user.*
import java.util.*

object UserFactory {
    fun create(
        id: UUID,
        accountId: String,
        name: String,
        description: String
    ) =
        User(
            Id(id),
            AccountId(accountId),
            Name(name),
            Description(description)
        )
}