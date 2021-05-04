package com.uramnoil.serverist.domain.services.unauthenticateduser

import com.uramnoil.serverist.domain.models.unauthenticateduser.AccountId
import com.uramnoil.serverist.domain.models.unauthenticateduser.Email
import com.uramnoil.serverist.domain.models.unauthenticateduser.UnauthenticatedUser
import com.uramnoil.serverist.domain.models.user.HashedPassword

object UnauthenticatedUserFactory {
    fun create(accountId: String, email: String, password: String) = UnauthenticatedUser(
        accountId = AccountId(accountId),
        email = Email(email),
        hashedPassword = HashedPassword(password)
    )
}