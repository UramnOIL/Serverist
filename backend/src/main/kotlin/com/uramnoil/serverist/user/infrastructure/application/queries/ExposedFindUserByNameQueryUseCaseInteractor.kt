package com.uramnoil.serverist.user.infrastructure.application.queries

import com.uramnoil.serverist.user.application.User
import com.uramnoil.serverist.user.application.queries.FindUserByNameQueryUseCaseInputPort
import com.uramnoil.serverist.user.infrastructure.Users
import com.uramnoil.serverist.user.infrastructure.application.toApplicationUser
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*

class ExposedFindUserByNameQueryUseCaseInteractor :
    FindUserByNameQueryUseCaseInputPort {
    override suspend fun execute(name: String, serversLimit: Long): Result<User?> {
        val rowOrNull = runCatching {
            newSuspendedTransaction {
                Users.select { Users.name.lowerCase() eq name.lowercase(Locale.getDefault()) }.firstOrNull()
            }
        }
        return rowOrNull.map { it?.toApplicationUser() }
    }
}