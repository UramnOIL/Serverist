package com.uramnoil.serverist.domain.repositories

import com.uramnoil.serverist.domain.models.email.User
import com.uramnoil.serverist.domain.models.kernel.user.Id

interface EmailRepository {
    suspend fun store(user: User)
    suspend fun findById(id: Id): User
}