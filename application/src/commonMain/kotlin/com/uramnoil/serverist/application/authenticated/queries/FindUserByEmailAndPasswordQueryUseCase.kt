package com.uramnoil.serverist.application.authenticated.queries

import com.benasher44.uuid.Uuid


/**
 *
 */
interface FindUserByEmailAndPasswordQueryUseCaseInputPort {
    /**
     *
     */
    fun execute(mail: String, password: String)
}

/**
 *
 */
fun interface FindUserByEmailAndPasswordQueryUseCaseOutputPort {
    /**
     *
     */
    fun handle(result: Result<Uuid?>)
}