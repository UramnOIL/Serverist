package com.uramnoil.serverist.auth.application.authenticated.queries

import java.util.*


/**
 *
 */
interface FindUserByEmailAndPasswordQueryUseCaseInputPort {
    /**
     *
     */
    suspend fun execute(mail: String, password: String)
}

/**
 *
 */
fun interface FindUserByEmailAndPasswordQueryUseCaseOutputPort {
    /**
     *
     */
    suspend fun handle(result: Result<UUID?>)
}