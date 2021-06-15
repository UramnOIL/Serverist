package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.user.User
import com.uramnoil.serverist.application.user.queries.ValidateLoginService
import com.uramnoil.serverist.application.user.queries.ValidateLoginServiceDto
import com.uramnoil.serverist.application.user.toApplication
import com.uramnoil.serverist.domain.kernel.models.HashedPassword
import com.uramnoil.serverist.domain.kernel.models.Password
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedValidateLoginService(
    private val hashPasswordService: com.uramnoil.serverist.domain.kernel.services.HashPasswordService
) : ValidateLoginService {
    override suspend fun execute(dto: ValidateLoginServiceDto): User? {
        val result = newSuspendedTransaction {
            Users.select { (Users.accountId eq dto.accountIdOrEmail) or (Users.email eq dto.accountIdOrEmail) }
                .firstOrNull()
        }

        val user = result?.let(ResultRow::toDomainUser) ?: return null

        return if (hashPasswordService.check(
                Password(dto.password),
                HashedPassword(user.hashedPassword.value)
            )
        ) {
            user.toApplication()
        } else {
            null
        }
    }
}