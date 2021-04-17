package com.uramnoil.serverist.domain.login.services

import com.uramnoil.serverist.domain.login.models.user.HashPassword
import com.uramnoil.serverist.domain.login.models.user.User
import com.uramnoil.serverist.domain.login.models.user.UserId
import kotlinx.coroutines.Deferred

interface CreateUserService {
    fun newAsync(id: UserId, password: HashPassword): Deferred<User>
}