package com.uramnoil.serverist.application.server

import com.uramnoil.serverist.application.OrderBy
import com.uramnoil.serverist.application.Sort

/**
 *
 */
data class FindAllServersUseCaseInput(val limit: Int, val offset: Long, val sort: Sort, val orderBy: OrderBy)

/**
 *
 */
fun interface FindAllServersUseCaseInputPort {
    fun execute(input: FindAllServersUseCaseInput)
}

/**
 *
 */
data class FindAllServersUseCaseOutput(val result: Result<List<Server>>)

/**
 *
 */
fun interface FindAllServersUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: FindAllServersUseCaseOutput)
}
