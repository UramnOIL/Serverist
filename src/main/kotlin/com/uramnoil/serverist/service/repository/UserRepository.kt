package com.uramnoil.serverist.service.repository

import com.uramnoil.serverist.service.model.user.Id
import com.uramnoil.serverist.service.model.user.User


interface UserRepository {
    suspend fun findById(id: Id): User
    suspend fun getNextId(): Id
    suspend fun store(user: User)
    suspend fun remove(user: User)
}