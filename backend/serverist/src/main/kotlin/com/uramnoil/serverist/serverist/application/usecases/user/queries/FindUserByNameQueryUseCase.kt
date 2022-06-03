package com.uramnoil.serverist.serverist.application.usecases.user.queries


/**
 *
 */
interface FindUserByNameQueryUseCaseInputPort {
    /**
     *
     */
    suspend fun execute(name: String, serversLimit: Long): Result<User?>
}

