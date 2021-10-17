package com.uramnoil.serverist.application.user.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.user.User

interface FindUserByIdQueryInputPort {
    fun execute(id: Uuid)
}

interface FindUserByIdQueryOutputPort {
    fun handle(result: Result<User?>)
}