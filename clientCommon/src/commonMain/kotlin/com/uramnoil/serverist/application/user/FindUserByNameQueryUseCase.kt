package com.uramnoil.serverist.application.user

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
fun interface FindUserByNameQueryUseCaseOutputPort {
    /**
     *
     */
    fun handle(result: Result<User?>)
}