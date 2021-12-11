package com.uramnoil.serverist.application.server

import com.benasher44.uuid.Uuid

/**
 *
 */
data class FindServerByIdQueryUseCaseInput(val id: Uuid)

/**
 *
 */
interface FindServerByIdQueryUseCaseInputPort {
    fun execute(input: FindServerByIdQueryUseCaseInput)
}

/**
 *
 */
/**
 *
 */
data class FindServerByIdQueryUseCaseOutput(private val result: Result<Server?>)

/**
 *
 */
fun interface FindServerByIdQueryUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: FindServerByIdQueryUseCaseOutput)
}