package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User

actual interface FindUserByAccountIdQuery {
    suspend fun execute(accountId: String): Result<User?>
}