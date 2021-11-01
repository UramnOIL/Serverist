package com.uramnoil.serverist.infrastructure.user.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.application.auth.authenticated.TryLoginUseCaseInputPort
import com.uramnoil.serverist.domain.auth.kernel.model.HashedPassword
import com.uramnoil.serverist.domain.auth.kernel.model.Password
import com.uramnoil.serverist.domain.auth.kernel.services.HashPasswordService
import com.uramnoil.serverist.infrastracture.auth.authenticated.Users
import com.uramnoil.serverist.infrastructure.auth.authenticated.toApplicationAuthenticatedUser
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedTryLoginInteractor(private val hashPasswordService: HashPasswordService) : TryLoginUseCaseInputPort {
    override suspend fun execute(accountIdOrEmail: String, password: String): Result<Uuid?> {
        val rowResult = kotlin.runCatching {
            newSuspendedTransaction {
                Users.select { (Users.email eq accountIdOrEmail) }.firstOrNull()
            }
        }

        val row = rowResult.getOrElse {
            return Result.failure(it)
        }

        // not existing
        row ?: run {
            return Result.failure(IllegalArgumentException("Could not find user which matches id or email."))
        }

        val user = row.toApplicationAuthenticatedUser()

        // check password
        val isSamePassword = hashPasswordService.check(Password(password), HashedPassword(user.hashedPassword))

        val idOrNull = if (isSamePassword) user.id else null
        return Result.success(idOrNull)
    }
}