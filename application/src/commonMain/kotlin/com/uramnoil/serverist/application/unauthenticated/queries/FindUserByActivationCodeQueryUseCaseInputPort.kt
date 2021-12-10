package com.uramnoil.serverist.application.unauthenticated.queries

import com.benasher44.uuid.Uuid

/**
 *
 */
interface FindUserByActivationCodeQueryUseCaseInputPort {
    /**
     *
     */
    fun execute(activationCode: Uuid)
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