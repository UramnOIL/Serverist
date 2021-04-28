package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.domain.models.user.AccountId
import com.uramnoil.serverist.domain.models.user.Description
import com.uramnoil.serverist.domain.models.user.Id
import com.uramnoil.serverist.domain.models.user.Name
import com.uramnoil.serverist.domain.repositories.UserRepository
import com.uramnoil.serverist.domain.services.user.CreateUserService
import com.uramnoil.serverist.domain.services.user.Email
import com.uramnoil.serverist.domain.services.user.HashedPassword
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.coroutines.CoroutineContext

class ExposedCreateUserService(
    private val database: Database,
    private val repository: UserRepository,
    context: CoroutineContext
) :
    CreateUserService, CoroutineScope by CoroutineScope(context) {
    override suspend fun new(
        accountId: AccountId,
        email: Email,
        password: HashedPassword,
        name: Name,
        description: Description
    ) = newSuspendedTransaction(db = database) {
        val id = Id(Users.insertAndGetId {
            it[Users.name] = Users.name
            it[Users.description] = Users.description
        }.value)
        commit()
        id
    }
}