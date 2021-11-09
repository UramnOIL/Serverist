package com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.queries

import com.uramnoil.serverist.auth.application.unauthenticated.queries.FindUserByEmailQueryUseCaseInputPort
import com.uramnoil.serverist.auth.application.unauthenticated.queries.User
import com.uramnoil.serverist.auth.infrastructure.unauthenticated.Users
import com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.toApplicationUnauthenticatedUser
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class FindUserByEmailQueryUseCaseInteractor : FindUserByEmailQueryUseCaseInputPort {
    override suspend fun execute(email: String): Result<User?> = kotlin.runCatching {
        val row = newSuspendedTransaction {
            Users.select { Users.email eq email }.firstOrNull()
        }
        row?.toApplicationUnauthenticatedUser()
    }
}