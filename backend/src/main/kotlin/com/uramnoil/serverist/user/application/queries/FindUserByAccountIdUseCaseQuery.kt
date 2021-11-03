package com.uramnoil.serverist.user.application.queries

import com.uramnoil.serverist.user.application.User

interface FindUserByAccountIdQueryUseCaseInputPort {
    suspend fun execute(accountId: String): Result<User?>
}