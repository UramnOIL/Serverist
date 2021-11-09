package com.uramnoil.serverist.domain.user.repositories

import com.uramnoil.serverist.domain.common.user.Id
import com.uramnoil.serverist.domain.user.models.User


interface UserRepository {
    suspend fun insert(user: User): Result<Unit>
    suspend fun update(user: User): Result<Unit>
    suspend fun delete(user: User): Result<Unit>
    suspend fun findById(id: Id): Result<User?>
}