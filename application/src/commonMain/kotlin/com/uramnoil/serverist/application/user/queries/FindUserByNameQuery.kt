package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User

interface FindUserByNameQueryInputPort {
    fun execute(name: String, serversLimit: Long)
}

interface FindUserByNameQueryOutputPort {
    fun handle(result: Result<User?>)
}