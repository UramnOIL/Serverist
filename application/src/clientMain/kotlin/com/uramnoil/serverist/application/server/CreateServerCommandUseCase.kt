package com.uramnoil.serverist.application.server

import com.benasher44.uuid.Uuid


/**
 * A input-port of CreateServerCommandUseCase.
 */
/**
 *
 */
data class CreateServerCommandUseCaseInput(
    val name: String,
    val host: String?,
    val port: UShort?,
    val description: String
)

/**
 *
 */
fun interface CreateServerCommandUseCaseInputPort {
    fun execute(input: CreateServerCommandUseCaseInput)
}

/**
 *
 */
data class CreateServerCommandUseCaseOutput(val result: Result<Uuid>)

/**
 *
 */
fun interface CreateServerCommandUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: CreateServerCommandUseCaseOutput)
}