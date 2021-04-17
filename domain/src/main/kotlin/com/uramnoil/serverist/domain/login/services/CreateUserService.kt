package com.uramnoil.serverist.domain.login.services

import com.uramnoil.serverist.domain.login.models.user.HashPassword
import com.uramnoil.serverist.domain.login.models.user.Id
import com.uramnoil.serverist.domain.login.models.user.User
import kotlinx.coroutines.Deferred

interface CreateUserService {
    fun newAsync(id: Id, password: HashPassword): Deferred<User>
}