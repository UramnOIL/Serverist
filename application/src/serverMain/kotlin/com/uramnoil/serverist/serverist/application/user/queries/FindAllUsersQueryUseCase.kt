package com.uramnoil.serverist.serverist.application.user.queries

import com.uramnoil.serverist.serverist.application.user.User

/**
 *
 */
interface FindAllUsersQueryUseCaseInputPort {
    /**
     *
     */
    fun execute()
}

/**
 *
 */
fun interface FindAllUsersQueryUseCaseOutputPort {
    /**
     *
     */
    fun handle(result: Result<List<User>>)
}
