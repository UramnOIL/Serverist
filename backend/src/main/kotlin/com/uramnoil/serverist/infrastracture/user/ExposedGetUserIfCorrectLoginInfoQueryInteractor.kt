package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.user.User
import com.uramnoil.serverist.application.user.queries.GetUserIfCorrectLoginInfoQueryInputPort
import com.uramnoil.serverist.application.user.toApplication
import com.uramnoil.serverist.domain.kernel.models.user.HashedPassword
import com.uramnoil.serverist.domain.kernel.models.user.Password
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedGetUserIfCorrectLoginInfoQueryInteractor(
    private val hashPasswordService: com.uramnoil.serverist.domain.kernel.services.HashPasswordService
) : GetUserIfCorrectLoginInfoQueryInputPort {
    override suspend fun execute(accountIdOrEmail: String, password: String): Result<User?> {
        val result = kotlin.runCatching {
            newSuspendedTransaction {
                Users.select { (Users.accountId eq accountIdOrEmail) or (Users.email eq accountIdOrEmail) }
                    .firstOrNull()
            }
        }.map {
            it?.let(ResultRow::toDomainUser) ?: return Result.failure(IllegalArgumentException("E-Mailとパスワードが一致しません。"))
        }

        val user = result.getOrElse {
            return Result.failure(it)
        }

        val isCorrectPassword = hashPasswordService.check(Password(password), HashedPassword(user.hashedPassword.value))

        return Result.success(if (isCorrectPassword) user.toApplication() else null)
    }
}