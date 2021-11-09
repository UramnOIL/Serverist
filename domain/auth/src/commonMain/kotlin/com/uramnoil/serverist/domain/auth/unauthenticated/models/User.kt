package com.uramnoil.serverist.domain.auth.unauthenticated.models

import com.uramnoil.serverist.domain.auth.kernel.model.Email
import com.uramnoil.serverist.domain.auth.kernel.model.HashedPassword


class User private constructor(
    val id: Id,
    val email: Email,
    val hashedPassword: HashedPassword,
    val activationCode: ActivationCode,
) {
    companion object {
        fun new(
            id: Id,
            email: Email,
            hashedPassword: HashedPassword,
            activationCode: ActivationCode,
        ): Result<User> = runCatching { User(id, email, hashedPassword, activationCode) }
    }
}