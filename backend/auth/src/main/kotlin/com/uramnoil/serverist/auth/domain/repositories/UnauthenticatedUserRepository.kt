package com.uramnoil.serverist.auth.domain.repositories

import com.uramnoil.serverist.auth.domain.models.unauthenticated.UnauthenticatedUser
import com.uramnoil.serverist.auth.domain.unauthenticated.models.Id

interface UnauthenticatedUserRepository {
    suspend fun insert(user: UnauthenticatedUser): Result<Unit>
    suspend fun update(user: UnauthenticatedUser): Result<Unit>
    suspend fun delete(user: UnauthenticatedUser): Result<Unit>
    suspend fun findById(id: Id): Result<UnauthenticatedUser?>
}