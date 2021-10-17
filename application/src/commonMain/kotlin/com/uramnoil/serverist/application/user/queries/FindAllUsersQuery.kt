package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User

interface FindAllUsersQueryInputPort {
    fun execute()
}

interface FindAllUsersQueryOutputPort {
    fun handle(result: Result<List<User>>)
}