package com.uramnoil.serverist.application.server

import com.benasher44.uuid.Uuid


/**
 *
 */
data class UpdateServerUseCaseInput(
    val id: Uuid,
    val name: String,
    val host: String?,
    val port: UShort?,
    val description: String
)

/**
 *
 */
fun interface UpdateServerUseCaseInputPort {
    fun execute(input: UpdateServerUseCaseInput)
}

/**
 *
 */
data class UpdateServerUseCaseOutput(val result: Result<Unit>)

/**
 *
 */
fun interface UpdateServerUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: UpdateServerUseCaseOutput)
}