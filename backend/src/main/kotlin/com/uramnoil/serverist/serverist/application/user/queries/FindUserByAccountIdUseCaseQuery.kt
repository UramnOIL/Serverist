package com.uramnoil.serverist.serverist.application.user.queries

import com.uramnoil.serverist.serverist.application.user.User

/**
 *
 */
interface FindUserByAccountIdQueryUseCaseInputPort {
    /**
     *
     */
    suspend fun execute(accountId: String)
}

interface FindUserByAccountIdQueryUseCaseOutputPort {
    /**
     *
     */
    suspend fun handle(result: Result<User?>)
}