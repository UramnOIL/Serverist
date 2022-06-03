package com.uramnoil.serverist.application.server

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.OrderBy
import com.uramnoil.serverist.application.Sort

/**
 *
 */
data class FindServersByOwnerUseCaseInput(
    val ownerId: Uuid,
    val limit: Int,
    val offset: Long,
    val sort: Sort,
    val orderBy: OrderBy
)

/**
 *
 */
fun interface FindServersByOwnerUseCaseInputPort {
    fun execute(input: FindServersByOwnerUseCaseInput)
}

/**
 *
 */
data class FindServersByOwnerUseCaseOutput(val result: Result<List<Server>>)

/**
 *
 */
fun interface FindServersByOwnerUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: FindServersByOwnerUseCaseOutput)
}
