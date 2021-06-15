package com.uramnoil.serverist.domain.user.repositories

import com.uramnoil.serverist.domain.user.models.User

interface UserRepository {
    suspend fun insert(user: User)
    suspend fun update(user: User)
    suspend fun delete(user: User)
    suspend fun findById(id: com.uramnoil.serverist.domain.kernel.models.UserId): User?
}