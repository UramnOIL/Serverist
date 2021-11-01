package com.uramnoil.serverist.infrastructure.user.queries

import com.uramnoil.serverist.application.user.User
import com.uramnoil.serverist.application.user.queries.FindAllUsersQueryUseCaseInputPort
import com.uramnoil.serverist.infrastracture.user.toApplicationUser
import com.uramnoil.serverist.infrastructure.user.Users
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