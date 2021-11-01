package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User

interface FindUserByNameQueryUseCaseInputPort {
    suspend fun execute(name: String, serversLimit: Long): Result<User?>
}