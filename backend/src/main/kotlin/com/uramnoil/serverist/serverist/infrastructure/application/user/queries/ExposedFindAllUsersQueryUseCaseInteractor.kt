package com.uramnoil.serverist.serverist.infrastructure.application.user.queries

import com.uramnoil.serverist.serverist.application.user.User
import com.uramnoil.serverist.serverist.application.user.queries.FindAllUsersQueryUseCaseInputPort
import com.uramnoil.serverist.serverist.infrastructure.Users
import com.uramnoil.serverist.serverist.user.infrastructure.application.toApplicationUser
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedFindAllUsersQueryUseCaseInteractor : FindAllUsersQueryUseCaseInputPort {
    override suspend fun execute(): Result<List<User>> {
        val result = runCatching {
            newSuspendedTransaction {
                Users.selectAll().map { it.toApplicationUser() }
            }
        }
        return result
    }
}