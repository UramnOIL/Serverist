package com.uramnoil.serverist.domain.services.user

import com.uramnoil.serverist.domain.models.user.*
import kotlinx.coroutines.Deferred

interface CreateUserService {
    fun newAsync(
        accountId: AccountId,
        email: Email,
        password: HashedPassword,
        name: Name,
        description: Description
    ): Deferred<Id>
}