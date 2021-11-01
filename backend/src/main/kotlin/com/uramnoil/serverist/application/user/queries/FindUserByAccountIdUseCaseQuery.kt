package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User

interface FindUserByAccountIdQueryUseCaseInputPort {
    suspend fun execute(accountId: String): Result<User?>
}