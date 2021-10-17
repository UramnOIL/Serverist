package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User

interface FindUserByNameQueryUseCaseInputPort {
    fun execute(name: String, serversLimit: Long)
}

interface FindUserByNameQueryUseCaseOutputPort {
    fun handle(result: Result<User?>)
}