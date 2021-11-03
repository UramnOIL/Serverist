package com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.queries

import com.uramnoil.serverist.auth.application.unauthenticated.queries.FindUserByActivationCodeQueryUseCaseInputPort
import com.uramnoil.serverist.auth.infrastructure.unauthenticated.Users
import com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.toDomainUnauthenticatedUser
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*

class FindUserByActivationCodeQueryUseCaseInteractor :
    FindUserByActivationCodeQueryUseCaseInputPort {
    override suspend fun execute(activationCode: UUID): Result<UUID?> = kotlin.runCatching {
        val user = newSuspendedTransaction {
            val row = Users.select { Users.activateCode eq activationCode }.firstOrNull()
            row?.toDomainUnauthenticatedUser()
        }
        user?.getOrNull()?.id?.value
    }
}