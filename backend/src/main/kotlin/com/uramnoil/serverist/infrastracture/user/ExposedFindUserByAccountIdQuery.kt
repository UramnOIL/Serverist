package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.user.User
import com.uramnoil.serverist.application.user.queries.FindUserByAccountIdQuery
import com.uramnoil.serverist.application.user.queries.FindUserByAccountIdQueryDto
import com.uramnoil.serverist.application.user.queries.FindUserByAccountIdQueryOutputPort
import com.uramnoil.serverist.application.user.queries.FindUserByAccountIdQueryOutputPortDto
import com.uramnoil.serverist.domain.services.user.UserFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.coroutines.CoroutineContext

class ExposedFindUserByAccountIdQuery(
    private val database: Database,
    private val outputPort: FindUserByAccountIdQueryOutputPort,
    context: CoroutineContext
) : FindUserByAccountIdQuery, CoroutineScope by CoroutineScope(context) {
    override fun execute(dto: FindUserByAccountIdQueryDto) {
        launch {
            val result = newSuspendedTransaction(db = database) {
                Users.select { Users.accountId eq dto.accountId }.firstOrNull()
            }

            val user = result?.let {
                UserFactory.create(
                    it[Users.id].value,
                    it[Users.accountId],
                    it[Users.name],
                    it[Users.description]
                )
            }

            outputPort.handle(FindUserByAccountIdQueryOutputPortDto(
                user?.let {
                    User(
                        id = it.id.value.toString(),
                        accountId = it.accountId.value,
                        name = it.name.value,
                        description = it.description.value
                    )
                }
            ))
        }
    }
}