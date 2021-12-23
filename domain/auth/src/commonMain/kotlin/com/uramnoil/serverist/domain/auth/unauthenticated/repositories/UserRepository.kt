package com.uramnoil.serverist.domain.auth.unauthenticated.repositories

import com.uramnoil.serverist.domain.auth.unauthenticated.models.Id
import com.uramnoil.serverist.domain.auth.unauthenticated.models.User

interface UserRepository {
    suspend fun insert(user: User): Result<Unit>
    suspend fun update(user: User): Result<Unit>
    suspend fun delete(user: User): Result<Unit>
    suspend fun findById(id: Id): Result<User?>
}