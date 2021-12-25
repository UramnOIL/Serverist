package com.uramnoil.serverist.application.user

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.serverist.application.user.User

/**
 *
 */
data class FindUserByIdUseCaseInput(val id: Uuid)

/**
 *
 */
fun interface FindUserByIdUseCaseInputPort {
    fun execute(input: FindUserByIdUseCaseInput)
}

/**
 *
 */
data class FindUserByIdUseCaseOutput(val result: Result<User?>)

/**
 *
 */
fun interface FindUserByIdUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: FindUserByIdUseCaseOutput)
}
