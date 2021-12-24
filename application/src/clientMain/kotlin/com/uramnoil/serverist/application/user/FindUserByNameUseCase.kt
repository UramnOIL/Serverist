package com.uramnoil.serverist.application.user

import com.uramnoil.serverist.serverist.application.user.User

/**
 *
 */
data class FindUserByNameUseCaseInput(val name: String, val serversLimit: Long)

/**
 *
 */
fun interface FindUserByNameUseCaseInputPort {
    fun execute(input: FindUserByNameUseCaseInput)
}

/**
 *
 */
data class FindUserByNameUseCaseOutput(val result: Result<User?>)

/**
 *
 */
fun interface FindUserByNameUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: FindUserByNameUseCaseOutput)
}
