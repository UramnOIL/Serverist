package com.uramnoil.serverist.application.auth

fun interface WithdrawalUseCaseInputPort {
    fun execute()
}

/**
 *
 */
data class WithdrawalUseCaseOutput(private val result: Result<Unit>)

/**
 *
 */
fun interface WithdrawalUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: WithdrawalUseCaseOutput)
}