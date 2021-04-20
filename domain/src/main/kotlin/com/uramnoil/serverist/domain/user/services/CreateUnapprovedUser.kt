package com.uramnoil.serverist.domain.user.services

import com.uramnoil.serverist.domain.user.model.unapproveduser.Password
import com.uramnoil.serverist.domain.user.model.unapproveduser.User
import com.uramnoil.serverist.domain.user.model.unapproveduser.UserId
import kotlinx.coroutines.Deferred

interface CreateUnapprovedUser {
    fun createAsync(userId: UserId, password: Password): Deferred<User>
}