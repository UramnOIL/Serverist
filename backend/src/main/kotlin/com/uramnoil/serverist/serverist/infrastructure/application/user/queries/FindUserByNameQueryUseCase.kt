package com.uramnoil.serverist.serverist.infrastructure.application.user.queries

import com.uramnoil.serverist.serverist.application.user.User

/**
 *
 */
interface FindUserByNameQueryUseCaseInputPort {
    /**
     *
     */
    fun execute(name: String, serversLimit: Long)
}

/**
 *
 */
fun interface FindUserByNameQueryUseCaseOutputPort {
    /**
     *
     */
    fun handle(result: Result<User?>)
}