package com.uramnoil.serverist.serverist.user.application.queries

import com.uramnoil.serverist.serverist.user.application.User

interface FindAllUsersQueryUseCaseInputPort {
    suspend fun execute(): Result<List<User>>
}