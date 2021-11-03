package com.uramnoil.serverist.serverist.user.application.queries

import com.uramnoil.serverist.serverist.user.application.User

interface FindUserByNameQueryUseCaseInputPort {
    suspend fun execute(name: String, serversLimit: Long): Result<User?>
}