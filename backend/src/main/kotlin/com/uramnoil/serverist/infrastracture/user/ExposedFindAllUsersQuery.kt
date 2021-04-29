package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.user.User
import com.uramnoil.serverist.application.user.queries.FindAllUsersOutputPort
import com.uramnoil.serverist.application.user.queries.FindAllUsersOutputPortDto
import com.uramnoil.serverist.application.user.queries.FindAllUsersQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.coroutines.CoroutineContext

class ExposedFindAllUsersQuery(
    private val database: Database,
    private val outputPort: FindAllUsersOutputPort,
    context: CoroutineContext
) : FindAllUsersQuery, CoroutineScope by CoroutineScope(context) {
    override fun execute() {
        launch {
            val result = newSuspendedTransaction(db = database) {
                Users.selectAll()
            }
            val users = result.map {
                User(it[Users.id].value.toString(), it[Users.accountId], it[Users.name], it[Users.description])
            }

            val outputPortDto = FindAllUsersOutputPortDto(users)
            outputPort.handle(outputPortDto)
        }
    }
}