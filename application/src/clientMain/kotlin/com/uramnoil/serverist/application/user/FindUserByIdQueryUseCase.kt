package com.uramnoil.serverist.application.user

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.serverist.application.user.User

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
data class FindUserByIdQueryUseCaseOutput(val result: Result<User?>)

/**
 *
 */
fun interface FindUserByIdQueryUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: FindUserByIdQueryUseCaseOutput)
}