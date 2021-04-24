package com.uramnoil.serverist.domain.repositories

import com.uramnoil.serverist.domain.models.user.Id
import com.uramnoil.serverist.domain.models.user.User
import kotlinx.coroutines.Deferred

interface UserRepository {
    fun storeAsync(user: User): Deferred<Unit>
    fun deleteAsync(user: User): Deferred<Unit>
    fun findByIdAsync(id: Id): Deferred<User?>
}