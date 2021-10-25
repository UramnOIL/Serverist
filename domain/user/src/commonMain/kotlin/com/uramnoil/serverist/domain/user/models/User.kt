package com.uramnoil.serverist.domain.user.models

import com.uramnoil.serverist.domain.common.user.UserId

class User private constructor(
    val id: UserId,
    val accountId: AccountId,
    var name: Name,
    var description: Description
) {
    companion object {
        fun new(
            id: UserId,
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