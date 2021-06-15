package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.kernel.User

interface FindAllUsersQuery {
    suspend fun execute(): List<User>
}