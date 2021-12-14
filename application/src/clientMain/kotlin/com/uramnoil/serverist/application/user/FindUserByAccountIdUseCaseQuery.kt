package com.uramnoil.serverist.application.user

import com.uramnoil.serverist.serverist.application.user.User

/**
 *
 */
data class FindUserByAccountIdQueryUseCaseInput(val accountId: String)

/**
 *
 */
fun interface FindUserByAccountIdQueryUseCaseInputPort {
    fun execute(input: FindUserByAccountIdQueryUseCaseInput)
}

/**
 *
 */
data class FindUserByAccountIdQueryUseCaseOutput(val result: Result<User?>)

/**
 *
 */
fun interface FindUserByAccountIdQueryUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: FindUserByAccountIdQueryUseCaseOutput)
}