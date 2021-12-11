package com.uramnoil.serverist.application.auth

import com.benasher44.uuid.Uuid

fun interface ActivateUseCaseInputPort {
    fun execute(code: Uuid)
}

/**
 *
 */
data class ActivateUseCaseOutput(private val result: Result<Unit>)

/**
 *
 */
fun interface ActivateUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: ActivateUseCaseOutput)
}