package com.uramnoil.serverist.domain.serverist.models.user

import com.uramnoil.serverist.domain.common.user.Id

class User private constructor(
    val id: Id,
    var accountId: AccountId,
    var name: Name,
    var description: Description
) {
    companion object {
        fun new(
            id: Id,
            accountId: AccountId,
            name: Name,
            description: Description
        ) = kotlin.runCatching {
            User(
                id,
                accountId,
                name,
                description
            )
        }
    }
}
