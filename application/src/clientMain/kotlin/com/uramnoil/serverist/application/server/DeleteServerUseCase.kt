package com.uramnoil.serverist.application.server

import com.benasher44.uuid.Uuid

/**
 *
 */
data class DeleteServerUseCaseInput(val id: Uuid)

/**
 *
 */
fun interface DeleteServerUseCaseInputPort {
    fun execute(input: DeleteServerUseCaseInput)
}

/**
 *
 */
data class DeleteServerUseCaseOutput(val result: Result<Unit>)

/**
 *
 */
fun interface DeleteServerUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: DeleteServerUseCaseOutput)
}
