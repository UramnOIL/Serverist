package com.uramnoil.serverist.infrastructure.user.queries

import com.uramnoil.serverist.application.user.User
import com.uramnoil.serverist.application.user.queries.FindUserByNameQueryUseCaseInputPort
import com.uramnoil.serverist.infrastracture.user.toApplicationUser
import com.uramnoil.serverist.infrastructure.user.Users
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