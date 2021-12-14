package com.uramnoil.serverist.application.server

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.serverist.application.server.Server

/**
 *
 */
data class FindServerByIdQueryUseCaseInput(val id: Uuid)

/**
 *
 */
fun interface FindServerByIdQueryUseCaseInputPort {
    fun execute(input: FindServerByIdQueryUseCaseInput)
}

/**
 *
 */
data class FindServerByIdQueryUseCaseOutput(val result: Result<Server?>)

/**
 *
 */
fun interface FindServerByIdQueryUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: FindServerByIdQueryUseCaseOutput)
}