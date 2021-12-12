package com.uramnoil.serverist.application.server

import com.benasher44.uuid.Uuid


/**
 *
 */
data class UpdateServerCommandUseCaseInput(
    val id: Uuid,
    val name: String,
    val host: String?,
    val port: UShort?,
    val description: String
)

/**
 *
 */
interface UpdateServerCommandUseCaseInputPort {
    fun execute(input: UpdateServerCommandUseCaseInput)
}

/**
 *
 */
data class UpdateServerCommandUseCaseOutput(val result: Result<Unit>)

/**
 *
 */
fun interface UpdateServerCommandUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: UpdateServerCommandUseCaseOutput)
}