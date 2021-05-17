package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.kernel.User
import com.uramnoil.serverist.application.user.queries.ValidateLoginService
import com.uramnoil.serverist.application.user.queries.ValidateLoginServiceDto
import com.uramnoil.serverist.domain.services.user.HashPasswordService
import com.uramnoil.serverist.domain.services.user.UserFactory
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedValidateLoginService(
    private val hashPasswordService: HashPasswordService
) : ValidateLoginService {
    override suspend fun execute(dto: ValidateLoginServiceDto): User? {
        val result = newSuspendedTransaction {
            Users.select { (Users.accountId eq dto.accountIdOrEmail) or (Users.email eq dto.accountIdOrEmail) }
                .firstOrNull()
        }

        val user = result?.let {
            UserFactory.create(
                it[Users.id].value,
                it[Users.accountId],
                it[Users.email],
                it[Users.hashedPassword],
                it[Users.name],
                it[Users.description]
            )
        } ?: return null

        return if (hashPasswordService.check(dto.password, user.hashedPassword.value)) {
            user.run {
                User(
                    id.value,
                    accountId.value,
                    name.value,
                    description.value
                )
            }
        } else {
            null
        }
    }
}