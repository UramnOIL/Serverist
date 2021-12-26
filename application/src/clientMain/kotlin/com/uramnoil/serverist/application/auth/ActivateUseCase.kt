package com.uramnoil.serverist.application.auth

/**
 *
 */
data class ActivateUseCaseInput(val email: String, val activationCode: String)

/**
 *
 */
fun interface ActivateUseCaseInputPort {
    fun execute(input: ActivateUseCaseInput)
}

/**
 *
 */
data class ActivateUseCaseOutput(val result: Result<Unit>)

/**
 *
 */
fun interface ActivateUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: ActivateUseCaseOutput)
}
