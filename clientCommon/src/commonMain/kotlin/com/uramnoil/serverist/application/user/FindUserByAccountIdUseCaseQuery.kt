package com.uramnoil.serverist.application.user

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