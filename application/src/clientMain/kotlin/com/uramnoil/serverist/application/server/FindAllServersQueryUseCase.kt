package com.uramnoil.serverist.application.server

import com.uramnoil.serverist.application.OrderBy
import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.serverist.application.server.Server

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
data class FindAllServersQueryUseCaseOutput(val result: Result<List<Server>>)

/**
 *
 */
fun interface FindAllServersQueryUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: FindAllServersQueryUseCaseOutput)
}