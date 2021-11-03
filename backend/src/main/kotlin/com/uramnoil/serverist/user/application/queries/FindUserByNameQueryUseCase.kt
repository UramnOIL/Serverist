package com.uramnoil.serverist.user.application.queries

import com.uramnoil.serverist.user.application.User

interface FindUserByNameQueryUseCaseInputPort {
    suspend fun execute(name: String, serversLimit: Long): Result<User?>
}