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
data class FindUserByNameQueryUseCaseOutput(private val result: Result<User?>)

/**
 *
 */
fun interface FindUserByNameQueryUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: FindUserByNameQueryUseCaseOutput)
}