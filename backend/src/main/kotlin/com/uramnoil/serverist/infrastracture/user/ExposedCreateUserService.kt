package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.domain.models.user.*
import com.uramnoil.serverist.domain.services.user.CreateUserService
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedCreateUserService : CreateUserService {
    override suspend fun new(
        accountId: AccountId,
        email: Email,
        hashedPassword: HashedPassword,
        name: Name,
        description: Description
    ) = newSuspendedTransaction {
        val id = Id(Users.insertAndGetId {
            it[Users.accountId] = accountId.value
            it[Users.email] = email.value
            it[Users.hashedPassword] = hashedPassword.value
            it[Users.name] = name.value
            it[Users.description] = description.value
        }.value)
        commit()
        id
    }
}