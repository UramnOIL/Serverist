package com.uramnoil.serverist.domain.user.services

import com.uramnoil.serverist.domain.user.models.user.HashPassword
import com.uramnoil.serverist.domain.user.models.user.User
import com.uramnoil.serverist.domain.user.models.user.UserId
import kotlinx.coroutines.Deferred

interface CreateUserService {
    fun newAsync(id: UserId, password: HashPassword): Deferred<User>
}