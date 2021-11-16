package com.uramnoil.serverist.auth.application.unauthenticated.queries

import java.util.*

/**
 *
 */
interface FindUserByActivationCodeQueryUseCaseInputPort {
    /**
     *
     */
    suspend fun execute(activationCode: UUID)
}

/**
 *
 */
fun interface FindUserByActivationCodeQueryUseCaseOutputPort {
    /**
     *
     */
    suspend fun handle(result: Result<User?>)
}