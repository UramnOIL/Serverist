package com.uramnoil.serverist.domain.models.user.repositories

import com.uramnoil.serverist.domain.models.kernel.models.UserId
import com.uramnoil.serverist.domain.models.user.models.User

interface UserRepository {
    suspend fun insert(user: User)
    suspend fun update(user: User)
    suspend fun delete(user: User)
    suspend fun findById(id: UserId): User?
}