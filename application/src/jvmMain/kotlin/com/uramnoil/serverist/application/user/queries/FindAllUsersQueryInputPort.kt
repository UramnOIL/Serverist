package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User

actual interface FindAllUsersQueryInputPort {
    suspend fun execute(): Result<List<User>>
}