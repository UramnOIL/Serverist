package com.uramnoil.serverist.serverist.application.user.queries

import com.uramnoil.serverist.serverist.application.user.User

/**
 *
 */
interface FindUserByAccountIdQueryUseCaseInputPort {
    /**
     *
     */
    fun execute(accountId: String)
}

/**
 *
 */
fun interface FindUserByAccountIdQueryUseCaseOutputPort {
    /**
     *
     */
    fun handle(result: Result<User?>)
}