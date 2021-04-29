package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.domain.models.kernel.user.Email
import com.uramnoil.serverist.domain.models.kernel.user.HashedPassword
import com.uramnoil.serverist.domain.models.kernel.user.Id
import com.uramnoil.serverist.domain.models.user.AccountId
import com.uramnoil.serverist.domain.models.user.Description
import com.uramnoil.serverist.domain.models.user.Name
import com.uramnoil.serverist.domain.services.user.CreateUserService
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedCreateUserService(private val database: Database) : CreateUserService {
    override suspend fun new(
        accountId: AccountId,
        email: Email,
        hashedPassword: HashedPassword,
        name: Name,
        description: Description
    ) = newSuspendedTransaction(db = database) {
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