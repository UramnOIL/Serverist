package com.uramnoil.serverist.application.user

import com.uramnoil.serverist.serverist.application.user.User

/**
 *
 */
data class FindUserByAccountIdUseCaseInput(val accountId: String)

/**
 *
 */
fun interface FindUserByAccountIdUseCaseInputPort {
    fun execute(input: FindUserByAccountIdUseCaseInput)
}

/**
 *
 */
data class FindUserByAccountIdUseCaseOutput(val result: Result<User?>)

/**
 *
 */
fun interface FindUserByAccountIdUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: FindUserByAccountIdUseCaseOutput)
}