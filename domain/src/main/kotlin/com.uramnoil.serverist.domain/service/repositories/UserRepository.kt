package com.uramnoil.serverist.domain.service.repositories

import com.uramnoil.serverist.domain.service.models.user.Id
import com.uramnoil.serverist.domain.service.models.user.User


interface UserRepository {
    suspend fun findById(id: Id): User
    suspend fun getNextId(): Id
    suspend fun store(user: User)
    suspend fun remove(user: User)
}