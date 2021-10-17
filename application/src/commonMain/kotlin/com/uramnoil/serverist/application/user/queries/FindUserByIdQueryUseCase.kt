package com.uramnoil.serverist.application.user.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.user.User

interface FindUserByIdQueryUseCaseInputPort {
    fun execute(id: Uuid)
}

interface FindUserByIdQueryUseCaseOutputPort {
    fun handle(result: Result<User?>)
}