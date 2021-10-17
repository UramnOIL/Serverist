package com.uramnoil.serverist.domain.auth.unauthenticated.models

import com.uramnoil.serverist.domain.auth.kernel.model.Email
import com.uramnoil.serverist.domain.auth.kernel.model.HashedPassword


class User private constructor(
    val userId: UserId,
    val email: Email,
    val hashedPassword: HashedPassword
) {
    companion object {
        fun new(
            userId: UserId,
            email: Email,
            hashedPassword: HashedPassword
        ): Result<User> = runCatching { User(userId, email, hashedPassword) }
    }
}