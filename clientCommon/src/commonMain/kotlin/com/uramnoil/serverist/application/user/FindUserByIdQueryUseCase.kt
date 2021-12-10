package com.uramnoil.serverist.application.user

import com.benasher44.uuid.Uuid

/**
 *
 */
data class FindUserByIdQueryUseCaseInput(val id: Uuid)

/**
 *
 */
interface FindUserByIdQueryUseCaseInputPort {
    fun execute(input: FindUserByIdQueryUseCaseInput)
}

/**
 *
 */
fun interface FindUserByIdQueryUseCaseOutputPort {
    /**
     *
     */
    fun handle(result: Result<User?>)
}