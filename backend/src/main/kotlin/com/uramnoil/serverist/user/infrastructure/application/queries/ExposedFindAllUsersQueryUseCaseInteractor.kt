package com.uramnoil.serverist.user.infrastructure.application.queries

import com.uramnoil.serverist.user.application.User
import com.uramnoil.serverist.user.application.queries.FindAllUsersQueryUseCaseInputPort
import com.uramnoil.serverist.user.infrastructure.Users
import com.uramnoil.serverist.user.infrastructure.application.toApplicationUser
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