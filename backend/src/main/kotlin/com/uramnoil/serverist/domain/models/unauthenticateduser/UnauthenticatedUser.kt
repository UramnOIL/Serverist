package com.uramnoil.serverist.domain.models.unauthenticateduser

import com.uramnoil.serverist.domain.models.user.HashedPassword

class UnauthenticatedUser(val accountId: AccountId, val email: Email, val hashedPassword: HashedPassword) {
}