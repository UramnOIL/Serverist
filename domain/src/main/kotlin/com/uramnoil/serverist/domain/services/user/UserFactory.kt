package com.uramnoil.serverist.domain.services.user

import com.uramnoil.serverist.domain.models.kernel.user.Id
import com.uramnoil.serverist.domain.models.user.AccountId
import com.uramnoil.serverist.domain.models.user.Description
import com.uramnoil.serverist.domain.models.user.Name
import com.uramnoil.serverist.domain.models.user.User
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