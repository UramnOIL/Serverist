package com.uramnoil.serverist.domain.services.server

import com.uramnoil.serverist.domain.models.server.*
import com.uramnoil.serverist.domain.models.user.User
import kotlinx.coroutines.Deferred

interface CreateServerService {
    fun newAsync(name: Name, owner: User, address: Address, port: Port, description: Description): Deferred<Id>
}