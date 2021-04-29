package com.uramnoil.serverist.domain.repositories

import com.uramnoil.serverist.domain.models.kernel.user.Id
import com.uramnoil.serverist.domain.models.user.User

interface UserRepository {
    suspend fun store(user: User)
    suspend fun delete(user: User)
    suspend fun findById(id: Id): User?
}