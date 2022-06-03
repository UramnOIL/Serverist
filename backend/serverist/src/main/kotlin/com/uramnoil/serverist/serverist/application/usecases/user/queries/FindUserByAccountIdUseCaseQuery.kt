package com.uramnoil.serverist.serverist.application.usecases.user.queries


/**
 *
 */
interface FindUserByAccountIdQueryUseCaseInputPort {
    /**
     *
     */
    suspend fun execute(accountId: String): Result<User?>
}

