package com.uramnoil.serverist.application.auth

class WithdrawalUseCaseInput

fun interface WithdrawalUseCaseInputPort {
    fun execute(input: WithdrawalUseCaseInput)
}

/**
 *
 */
data class WithdrawalUseCaseOutput(val result: Result<Unit>)

/**
 *
 */
fun interface WithdrawalUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: WithdrawalUseCaseOutput)
}