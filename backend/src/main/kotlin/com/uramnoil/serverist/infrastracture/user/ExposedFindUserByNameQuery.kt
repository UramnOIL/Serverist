package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.user.User
import com.uramnoil.serverist.application.user.queries.FindUserByNameDto
import com.uramnoil.serverist.application.user.queries.FindUserByNameOutputPort
import com.uramnoil.serverist.application.user.queries.FindUserByNameOutputPortDto
import com.uramnoil.serverist.application.user.queries.FindUserByNameQuery
import com.uramnoil.serverist.domain.services.user.UserFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.coroutines.CoroutineContext

class ExposedFindUserByNameQuery(
    private val database: Database,
    private val outputPort: FindUserByNameOutputPort,
    context: CoroutineContext
) : FindUserByNameQuery, CoroutineScope by CoroutineScope(context) {
    override fun execute(dto: FindUserByNameDto) {
        launch {
            outputPort.handle(newSuspendedTransaction(db = database) {
                val result = Users.select { Users.name.lowerCase() eq dto.name.toLowerCase() }.firstOrNull()
                    ?: return@newSuspendedTransaction null

                result.let {
                    UserFactory.create(
                        it[Users.id].value,
                        it[Users.accountId],
                        it[Users.email],
                        it[Users.hashedPassword],
                        it[Users.name],
                        it[Users.description],
                    )
                }
            }?.let {
                FindUserByNameOutputPortDto(
                    User(
                        id = it.id.value.toString(),
                        accountId = it.accountId.value,
                        email = it.email.value,
                        hashedPassword = it.hashedPassword.value.toString(),
                        name = it.name.value,
                        description = it.description.value
                    )
                )
            })
        }
    }
}