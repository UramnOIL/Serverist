package com.uramnoil.serverist.application.auth

fun interface ActivateUseCaseInputPort {
    fun execute(email: String, activationCode: String)
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
