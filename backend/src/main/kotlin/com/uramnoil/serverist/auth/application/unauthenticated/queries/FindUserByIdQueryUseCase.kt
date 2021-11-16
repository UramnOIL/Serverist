package com.uramnoil.serverist.auth.application.unauthenticated.queries

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