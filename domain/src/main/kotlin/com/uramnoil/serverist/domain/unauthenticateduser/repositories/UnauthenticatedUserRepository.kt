package com.uramnoil.serverist.domain.unauthenticateduser.repositories

import com.uramnoil.serverist.domain.unauthenticateduser.models.Id
import com.uramnoil.serverist.domain.unauthenticateduser.models.UnauthenticatedUser

interface UnauthenticatedUserRepository {
    suspend fun insert(user: UnauthenticatedUser)
    suspend fun update(user: UnauthenticatedUser)
    suspend fun delete(user: UnauthenticatedUser)
    suspend fun findById(id: Id): UnauthenticatedUser?
}