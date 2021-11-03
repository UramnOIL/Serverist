package com.uramnoil.serverist.user.application.queries

import com.uramnoil.serverist.user.application.User

interface FindAllUsersQueryUseCaseInputPort {
    suspend fun execute(): Result<List<User>>
}