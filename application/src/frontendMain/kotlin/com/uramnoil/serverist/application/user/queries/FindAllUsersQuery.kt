package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User

actual interface FindAllUsersQuery {
    fun execute(): List<User>
}

interface FindAllUsersQueryOutputPort {
    fun handle(result: Result<List<User>>)
}