package com.uramnoil.serverist.application.user

import com.uramnoil.serverist.serverist.application.user.User

/**
 *
 */
data class FindUserByNameQueryUseCaseInput(val name: String, val serversLimit: Long)

/**
 *
 */
interface FindUserByNameQueryUseCaseInputPort {
    fun execute(input: FindUserByNameQueryUseCaseInput)
}

/**
 *
 */
data class FindUserByNameQueryUseCaseOutput(val result: Result<User?>)

/**
 *
 */
fun interface FindUserByNameQueryUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: FindUserByNameQueryUseCaseOutput)
}