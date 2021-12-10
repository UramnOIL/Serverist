package com.uramnoil.serverist.application.server

import com.uramnoil.serverist.application.OrderBy
import com.uramnoil.serverist.application.Sort

/**
 *
 */
data class FindAllServersQueryUseCaseInput(val limit: Int, val offset: Long, val sort: Sort, val orderBy: OrderBy)

/**
 *
 */
interface FindAllServersQueryUseCaseInputPort {
    fun execute(input: FindAllServersQueryUseCaseInput)
}

/**
 *
 */
fun interface FindAllServersQueryUseCaseOutputPort {
    /**
     *
     */
    fun handle(result: Result<List<Server>>)
}