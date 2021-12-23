package com.uramnoil.serverist.application.user

import com.uramnoil.serverist.serverist.application.user.User

class FindAllUsersUseCaseInput

/**
 *
 */
fun interface FindAllUsersUseCaseInputPort {
    /**
     *
     */
    fun execute(input: FindAllUsersUseCaseInput)
}

/**
 *
 */
data class FindAllUsersUseCaseOutput(val result: Result<List<User>>)

/**
 *
 */
fun interface FindAllUsersUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: FindAllUsersUseCaseOutput)
}
