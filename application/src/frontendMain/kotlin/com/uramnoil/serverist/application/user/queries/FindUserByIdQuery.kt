package com.uramnoil.serverist.application.user.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.user.User

actual interface FindUserByIdQuery {
    fun execute(id: Uuid): User?
}

interface FindUserByIdQueryOutputPort {
    fun handle(result: Result<User?>)
}