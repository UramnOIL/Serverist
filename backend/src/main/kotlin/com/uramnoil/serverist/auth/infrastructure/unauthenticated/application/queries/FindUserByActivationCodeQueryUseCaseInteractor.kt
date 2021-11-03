package com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.queries

import com.uramnoil.serverist.auth.application.unauthenticated.queries.FindUserByActivationCodeQueryUseCaseInputPort
import com.uramnoil.serverist.auth.application.unauthenticated.queries.User
import com.uramnoil.serverist.auth.infrastructure.unauthenticated.Users
import com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.toApplicationUnauthenticatedUser
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*

class FindUserByActivationCodeQueryUseCaseInteractor :
    FindUserByActivationCodeQueryUseCaseInputPort {
    override suspend fun execute(activationCode: UUID): Result<User?> = kotlin.runCatching {
        val result = newSuspendedTransaction {
            val row = Users.select { Users.activateCode eq activationCode }.firstOrNull()
            row?.toApplicationUnauthenticatedUser()
        }
        result
    }
}