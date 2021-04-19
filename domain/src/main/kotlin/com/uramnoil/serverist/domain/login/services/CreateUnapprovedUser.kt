package com.uramnoil.serverist.domain.login.services

import com.uramnoil.serverist.domain.login.model.unapproveduser.User
import com.uramnoil.serverist.domain.login.model.unapproveduser.UserId
import kotlinx.coroutines.Deferred

interface CreateUnapprovedUser {
    fun createAsync(userId: UserId): Deferred<User>
}