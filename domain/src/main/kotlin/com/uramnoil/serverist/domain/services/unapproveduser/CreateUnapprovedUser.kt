package com.uramnoil.serverist.domain.services.unapproveduser

import com.uramnoil.serverist.domain.models.unapproveduser.AccountId
import com.uramnoil.serverist.domain.models.unapproveduser.Password
import com.uramnoil.serverist.domain.models.unapproveduser.User
import kotlinx.coroutines.Deferred

interface CreateUnapprovedUser {
    fun createAsync(userId: AccountId, password: Password): Deferred<User>
}