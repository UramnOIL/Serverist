package com.uramnoil.serverist.serverist.domain.models.user

import com.uramnoil.serverist.common.domain.models.kernel.UserId

class User private constructor(
    val userId: UserId,
    var accountId: AccountId,
    var name: Name,
    var description: Description
) {
    companion object {
        fun new(
            userId: UserId,
            accountId: AccountId,
            name: Name,
            description: Description
        ) = kotlin.runCatching {
            User(
                userId,
                accountId,
                name,
                description
            )
        }
    }
}
