package com.uramnoil.serverist.application.unauthenticated.queries


/**
 *
 */
interface FindUserByEmailQueryUseCaseInputPort {
    /**
     *
     */
    fun execute(email: String)
}

/**
 *
 */
fun interface FindUserByEmailQueryUseCaseOutputPort {
    /**
     *
     */
    fun handle(result: Result<User?>)
}