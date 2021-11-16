package com.uramnoil.serverist.serverist.application.user.queries

import com.uramnoil.serverist.serverist.application.user.User

/**
 *
 */
interface FindAllUsersQueryUseCaseInputPort {
    /**
     *
     */
    suspend fun execute()
}

interface FindAllUsersQueryUseCaseOutputPort {
    /**
     *
     */
    suspend fun handle(result: Result<List<User>>)
}