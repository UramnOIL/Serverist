package com.uramnoil.serverist.application.server

import com.benasher44.uuid.Uuid

/**
 * A input-port of CreateServerUseCase.
 */
/**
 *
 */
data class CreateServerUseCaseInput(
    val name: String,
    val host: String?,
    val port: UShort?,
    val description: String
)

/**
 *
 */
fun interface CreateServerUseCaseInputPort {
    fun execute(input: CreateServerUseCaseInput)
}

/**
 *
 */
data class CreateServerUseCaseOutput(val result: Result<Uuid>)

/**
 *
 */
fun interface CreateServerUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: CreateServerUseCaseOutput)
}
