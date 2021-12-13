package com.uramnoil.serverist.application.server

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.OrderBy
import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.serverist.application.server.Server

/**
 *
 */
data class FindServersByOwnerQueryUseCaseInput(
    val ownerId: Uuid,
    val limit: Int,
    val offset: Long,
    val sort: Sort,
    val orderBy: OrderBy
)

/**
 *
 */
interface FindServersByOwnerQueryUseCaseInputPort {
    fun execute(input: FindServersByOwnerQueryUseCaseInput)
}

/**
 *
 */
data class FindServersByOwnerQueryUseCaseOutput(val result: Result<List<Server>>)

/**
 *
 */
fun interface FindServersByOwnerQueryUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: FindServersByOwnerQueryUseCaseOutput)
}