package com.uramnoil.serverist.serverist.application.usecases.user.queries


/**
 *
 */
interface FindAllUsersQueryUseCaseInputPort {
    /**
     *
     */
    suspend fun execute(): Result<List<User>>
}

