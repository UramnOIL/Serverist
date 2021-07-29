package com.uramnoil.serverist.application.user.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.user.User

actual interface FindUserByIdQuery {
    suspend fun execute(id: Uuid): Result<User?>
}