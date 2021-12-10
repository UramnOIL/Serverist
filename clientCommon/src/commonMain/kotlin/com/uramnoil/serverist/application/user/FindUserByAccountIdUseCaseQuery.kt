package com.uramnoil.serverist.application.user

/**
 *
 */
data class FindUserByAccountIdQueryUseCaseInput(val accountId: String)

/**
 *
 */
interface FindUserByAccountIdQueryUseCaseInputPort {
    fun execute(input: FindUserByAccountIdQueryUseCaseInput)
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