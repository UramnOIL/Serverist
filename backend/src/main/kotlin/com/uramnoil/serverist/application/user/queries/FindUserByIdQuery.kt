package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User
import java.util.*

interface FindUserByIdQuery {
    suspend fun execute(id: UUID): User?
}