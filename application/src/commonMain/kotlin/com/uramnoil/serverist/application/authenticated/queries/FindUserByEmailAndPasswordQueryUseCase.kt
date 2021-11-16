package com.uramnoil.serverist.application.authenticated.queries

import java.util.*


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
    fun handle(result: Result<UUID?>)
}