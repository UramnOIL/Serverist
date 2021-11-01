package com.uramnoil.serverist.domain.user.models

import com.uramnoil.serverist.domain.common.user.Id

class User private constructor(
    val id: Id,
    val accountId: AccountId,
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