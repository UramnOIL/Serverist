package com.uramnoil.serverist.domain.service.services.server

import com.uramnoil.serverist.domain.service.models.server.Id
import com.uramnoil.serverist.domain.service.models.user.User
import kotlinx.coroutines.Deferred

interface CreateServerService {
    fun newAsync(name: String, owner: User, address: String?, port: Int?, description: String): Deferred<Id>
}