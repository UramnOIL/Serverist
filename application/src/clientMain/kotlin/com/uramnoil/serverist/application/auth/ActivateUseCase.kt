package com.uramnoil.serverist.application.auth

import com.benasher44.uuid.Uuid

/**
 *
 */
data class ActivateUseCaseInput(val email: String, val activationCode: String)

/**
 *
 */
fun interface ActivateUseCaseInputPort {
    fun execute(code: Uuid)
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
