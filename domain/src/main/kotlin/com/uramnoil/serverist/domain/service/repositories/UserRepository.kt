package com.uramnoil.serverist.domain.service.repositories

import com.uramnoil.serverist.domain.service.models.user.Id
import com.uramnoil.serverist.domain.service.models.user.User
import kotlinx.coroutines.Deferred


interface UserRepository {
    fun findByIdAsync(id: Id): Deferred<User>
    fun getNextId(): Id
    fun storeAsync(user: User): Deferred<Unit>
    fun removeAsync(user: User): Deferred<Unit>
}