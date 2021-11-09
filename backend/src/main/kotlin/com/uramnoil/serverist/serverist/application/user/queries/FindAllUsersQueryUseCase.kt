package com.uramnoil.serverist.serverist.application.user.queries

import com.uramnoil.serverist.serverist.application.user.User

interface FindAllUsersQueryUseCaseInputPort {
    suspend fun execute(): Result<List<User>>
}