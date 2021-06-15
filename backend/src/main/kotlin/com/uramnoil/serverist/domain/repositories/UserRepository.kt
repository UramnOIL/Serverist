package com.uramnoil.serverist.domain.repositories

import com.uramnoil.serverist.domain.models.kernel.UserId
import com.uramnoil.serverist.domain.models.user.User

interface UserRepository {
    suspend fun insert(user: User)
    suspend fun update(user: User)
    suspend fun delete(user: User)
    suspend fun findById(id: UserId): User?
}