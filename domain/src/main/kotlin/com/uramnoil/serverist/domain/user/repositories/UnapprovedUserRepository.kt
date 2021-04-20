package com.uramnoil.serverist.domain.user.repositories

import com.uramnoil.serverist.domain.user.model.unapproveduser.User
import kotlinx.coroutines.Deferred

interface UnapprovedUserRepository {
    fun storeAsync(user: User): Deferred<Unit>
    fun deleteAsync(remove: User): Deferred<Unit>
}