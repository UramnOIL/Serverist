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
/**
 *
 */
data class FindUserByAccountIdQueryUseCaseOutput(private val result: Result<User?>)

/**
 *
 */
fun interface FindUserByAccountIdQueryUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: FindUserByAccountIdQueryUseCaseOutput)
}