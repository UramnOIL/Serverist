package com.uramnoil.serverist.domain.service.services.user

import com.uramnoil.serverist.domain.service.models.user.Id
import kotlinx.coroutines.Deferred

interface CreateUserService {
    fun newAsync(name: String, description: String): Deferred<Id>
}