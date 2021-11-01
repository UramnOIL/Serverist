package com.uramnoil.serverist.domain.auth.authenticated.repositories

import com.uramnoil.serverist.domain.auth.authenticated.models.User
import com.uramnoil.serverist.domain.common.user.Id

interface UserRepository {
    suspend fun insert(user: User): Result<Unit>
    suspend fun update(user: User): Result<Unit>
    suspend fun delete(user: User): Result<Unit>
    suspend fun findById(id: Id): Result<User?>
}