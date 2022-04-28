package com.uramnoil.serverist.serverist.domain.repositories

import com.uramnoil.serverist.kernel.domain.user.Id
import com.uramnoil.serverist.serverist.domain.models.user.User

interface UserRepository {
    suspend fun insert(user: User): Result<Unit>
    suspend fun update(user: User): Result<Unit>
    suspend fun delete(user: User): Result<Unit>
    suspend fun findById(id: Id): Result<User?>
}
