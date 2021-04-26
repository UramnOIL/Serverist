package com.uramnoil.serverist.domain.services.unapproveduser

import com.uramnoil.serverist.domain.models.unapproveduser.AccountId
import com.uramnoil.serverist.domain.models.unapproveduser.Email
import com.uramnoil.serverist.domain.models.unapproveduser.Password
import com.uramnoil.serverist.domain.models.unapproveduser.User

object UnapprovedUserFactory {
    fun create(accountId: String, email: String, password: String) = User(
        accountId = AccountId(accountId),
        email = Email(email),
        password = Password(password)
    )
}