package com.uramnoil.serverist.domain.auth.authenticated.repositories

import com.uramnoil.serverist.domain.auth.authenticated.models.User
import com.uramnoil.serverist.domain.common.user.UserId

interface UserRepository {
    suspend fun insert(user: User): Result<Unit>
    suspend fun update(user: User): Result<Unit>
    suspend fun delete(user: User): Result<Unit>
    suspend fun findById(id: UserId): Result<User?>
}