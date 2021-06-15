package com.uramnoil.serverist.domain.models.unauthenticateduser.models

import com.uramnoil.serverist.domain.models.kernel.models.HashedPassword

class UnauthenticatedUser(val accountId: AccountId, val email: Email, val hashedPassword: HashedPassword) {
}