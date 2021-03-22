package com.uramnoil.serverist.domain.service.repositories

import com.uramnoil.serverist.domain.service.models.user.Id
import com.uramnoil.serverist.domain.service.models.user.User
import kotlinx.coroutines.Deferred


interface UserRepository {
    fun findById(id: Id): Deferred<User>
    fun getNextId(): Deferred<Id>
    fun store(user: User): Deferred<Unit>
    fun remove(user: User): Deferred<Unit>
}