package com.uramnoil.serverist.domain.login.services

import com.uramnoil.serverist.domain.login.models.unapproveduser.Password
import com.uramnoil.serverist.domain.login.models.unapproveduser.User
import com.uramnoil.serverist.domain.login.models.unapproveduser.UserId
import kotlinx.coroutines.Deferred

interface CreateUnapprovedUser {
    fun createAsync(userId: UserId, password: Password): Deferred<User>
}