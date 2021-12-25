package com.uramnoil.serverist.application.server

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.serverist.application.server.Server

/**
 *
 */
data class FindServerByIdUseCaseInput(val id: Uuid)

/**
 *
 */
fun interface FindServerByIdUseCaseInputPort {
    fun execute(input: FindServerByIdUseCaseInput)
}

/**
 *
 */
data class FindServerByIdUseCaseOutput(val result: Result<Server?>)

/**
 *
 */
fun interface FindServerByIdUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: FindServerByIdUseCaseOutput)
}