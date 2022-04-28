package com.uramnoil.serverist.auth.domain.models.unauthenticated

import com.uramnoil.serverist.auth.domain.unauthenticated.models.ActivationCode
import com.uramnoil.serverist.auth.domain.unauthenticated.models.Id

class UnauthenticatedUser private constructor(
    val id: Id,
    val email: com.uramnoil.serverist.auth.domain.models.Email,
    val hashedPassword: com.uramnoil.serverist.auth.domain.models.HashedPassword,
    val activationCode: ActivationCode,
) {
    companion object {
        fun new(
            id: Id,
            email: com.uramnoil.serverist.auth.domain.models.Email,
            hashedPassword: com.uramnoil.serverist.auth.domain.models.HashedPassword,
            activationCode: ActivationCode,
        ): Result<UnauthenticatedUser> = runCatching { UnauthenticatedUser(id, email, hashedPassword, activationCode) }
    }
}
