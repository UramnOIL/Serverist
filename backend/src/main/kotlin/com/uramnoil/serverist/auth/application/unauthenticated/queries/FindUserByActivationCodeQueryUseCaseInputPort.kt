package com.uramnoil.serverist.auth.application.unauthenticated.queries

import java.util.*

/**
 *
 */
interface FindUserByActivationCodeQueryUseCaseInputPort {
    /**
     *
     */
    fun execute(activationCode: UUID)
}

/**
 *
 */
fun interface FindUserByActivationCodeQueryUseCaseOutputPort {
    /**
     *
     */
    fun handle(result: Result<User?>)
}