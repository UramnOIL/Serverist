package com.uramnoil.serverist.serverist.application.user.queries

import com.uramnoil.serverist.serverist.application.user.User
import java.util.*

/**
 *
 */
interface FindUserByIdQueryUseCaseInputPort {
    /**
     *
     */
    suspend fun execute(id: UUID)
}

interface FindUserByIdQueryUseCaseOutputPort {
    /**
     *
     */
    suspend fun handle(result: Result<User?>)
}