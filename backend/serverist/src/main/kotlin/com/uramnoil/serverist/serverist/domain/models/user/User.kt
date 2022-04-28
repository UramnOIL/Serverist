package com.uramnoil.serverist.serverist.domain.models.user

import com.uramnoil.serverist.kernel.domain.user.Id

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
