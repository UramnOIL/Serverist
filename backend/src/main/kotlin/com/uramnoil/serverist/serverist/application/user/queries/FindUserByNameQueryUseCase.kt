package com.uramnoil.serverist.serverist.application.user.queries

import com.uramnoil.serverist.serverist.application.user.User

/**
 *
 */
interface FindUserByNameQueryUseCaseInputPort {
    /**
     *
     */
    suspend fun execute(name: String, serversLimit: Long)
}

/**
 *
 */
fun interface FindUserByNameQueryUseCaseOutputPort {
    /**
     *
     */
    suspend fun handle(result: Result<User?>)
}