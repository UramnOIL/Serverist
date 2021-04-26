package com.uramnoil.serverist.domain.repositories

import com.uramnoil.serverist.domain.models.unapproveduser.AccountId
import com.uramnoil.serverist.domain.models.unapproveduser.User
import kotlinx.coroutines.Deferred

interface UnapprovedUserRepository {
    fun storeAsync(user: User): Deferred<Unit>
    fun deleteAsync(remove: User): Deferred<Unit>
    fun findByAccountIdAsync(id: AccountId): Deferred<User?>
}