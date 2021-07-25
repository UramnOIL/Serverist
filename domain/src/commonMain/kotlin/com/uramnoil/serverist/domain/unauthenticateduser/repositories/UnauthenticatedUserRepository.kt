package com.uramnoil.serverist.domain.unauthenticateduser.repositories

import com.uramnoil.serverist.domain.unauthenticateduser.models.Id
import com.uramnoil.serverist.domain.unauthenticateduser.models.UnauthenticatedUser

interface UnauthenticatedUserRepository {
    suspend fun insert(user: UnauthenticatedUser): Result<Unit>
    suspend fun update(user: UnauthenticatedUser): Result<Unit>
    suspend fun delete(user: UnauthenticatedUser): Result<Unit>
    suspend fun findById(id: Id): Result<UnauthenticatedUser?>
}