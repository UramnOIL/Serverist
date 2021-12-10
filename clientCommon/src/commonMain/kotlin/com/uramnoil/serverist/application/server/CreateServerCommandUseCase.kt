package com.uramnoil.serverist.application.server

import com.benasher44.uuid.Uuid


/**
 * A input-port of CreateServerCommandUseCase.
 */
/**
 *
 */
data class CreateServerCommandUseCaseInput(
    val ownerId: Uuid,
    val name: String,
    val host: String?,
    val port: UShort?,
    val description: String
)

/**
 *
 */
interface CreateServerCommandUseCaseInputPort {
    fun execute(input: CreateServerCommandUseCaseInput)
}

/**
 *
 */
fun interface CreateServerCommandUseCaseOutputPort {
    /**
     *
     */
    fun handle(result: Result<Uuid>)
}