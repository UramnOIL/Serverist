package com.uramnoil.serverist.application.user

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