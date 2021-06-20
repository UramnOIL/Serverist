package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User

interface FindUserByAccountIdQuery {
    suspend fun execute(accountId: String): User?
}