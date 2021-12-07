package com.uramnoil.serverist.serverist.application.user.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.serverist.application.user.User

/**
 *
 */
interface FindUserByIdQueryUseCaseInputPort {
    /**
     *
     */
    fun execute(id: Uuid)
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