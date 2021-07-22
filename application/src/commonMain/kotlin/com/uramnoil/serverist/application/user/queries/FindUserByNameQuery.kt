package com.uramnoil.serverist.application.user.queries

import com.uramnoil.serverist.application.user.User

interface FindUserByNameQuery {
    suspend fun execute(name: String, serversLimit: Long): User?
}