package com.uramnoil.serverist.serverist.user.application.queries

import com.uramnoil.serverist.serverist.user.application.User

interface FindUserByAccountIdQueryUseCaseInputPort {
    suspend fun execute(accountId: String): Result<User?>
}