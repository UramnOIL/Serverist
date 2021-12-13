package com.uramnoil.serverist.application.user

import com.uramnoil.serverist.serverist.application.user.User

class FindAllUsersQueryUseCaseInput

/**
 *
 */
interface FindAllUsersQueryUseCaseInputPort {
    /**
     *
     */
    fun execute(input: FindAllUsersQueryUseCaseInput)
}

/**
 *
 */
data class FindAllUsersQueryUseCaseOutput(val result: Result<List<User>>)

/**
 *
 */
fun interface FindAllUsersQueryUseCaseOutputPort {
    /**
     *
     */
    fun handle(output: FindAllUsersQueryUseCaseOutput)
}