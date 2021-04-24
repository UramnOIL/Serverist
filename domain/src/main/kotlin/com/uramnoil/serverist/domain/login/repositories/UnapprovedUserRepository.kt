package com.uramnoil.serverist.domain.login.repositories

import com.uramnoil.serverist.domain.login.models.unapproveduser.User
import kotlinx.coroutines.Deferred

interface UnapprovedUserRepository {
    fun storeAsync(user: User): Deferred<Unit>
    fun deleteAsync(remove: User): Deferred<Unit>
}