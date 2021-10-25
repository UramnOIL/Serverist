package com.uramnoil.serverist.domain.auth.authenticated.models

import com.uramnoil.serverist.domain.auth.kernel.model.Email
import com.uramnoil.serverist.domain.auth.kernel.model.HashedPassword
import com.uramnoil.serverist.domain.common.user.UserId

class User private constructor(
    val id: UserId,
    var email: Email,
    var hashedPassword: HashedPassword,
) {
    companion object {
        fun new(
            id: UserId,
            email: Email,
            hashedPassword: HashedPassword,
        ) = kotlin.runCatching {
            User(
                id,
                email,
                hashedPassword,
            )
        }
    }
}