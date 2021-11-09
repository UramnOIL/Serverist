package com.uramnoil.serverist.auth.infrastructure.authenticated.application.queries

import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.auth.application.authenticated.queries.FindUserByEmailAndPasswordQueryUseCaseInputPort
import com.uramnoil.serverist.auth.infrastructure.authenticated.Users
import com.uramnoil.serverist.auth.infrastructure.authenticated.toApplicationAuthenticatedUser
import com.uramnoil.serverist.domain.auth.kernel.model.HashedPassword
import com.uramnoil.serverist.domain.auth.kernel.model.Password
import com.uramnoil.serverist.domain.auth.kernel.services.HashPasswordService
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedFindUserByEmailAndPasswordQueryInteractor(private val hashPasswordService: HashPasswordService) :
    FindUserByEmailAndPasswordQueryUseCaseInputPort {
    override suspend fun execute(mail: String, password: String): Result<Uuid?> {
        val rowResult = kotlin.runCatching {
            newSuspendedTransaction {
                Users.select { (Users.email eq mail) }.firstOrNull()
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