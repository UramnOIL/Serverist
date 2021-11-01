package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User

interface FindAllUsersQueryUseCaseInputPort {
    suspend fun execute(): Result<List<User>>
}