package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.user.queries.FindAllUsersOutputPort
import com.uramnoil.serverist.application.user.queries.FindAllUsersOutputPortDto
import com.uramnoil.serverist.application.user.queries.FindAllUsersQuery
import com.uramnoil.serverist.application.user.queries.UserDto
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
            newSuspendedTransaction(db = database) {
                outputPort.handle(FindAllUsersOutputPortDto(Users.selectAll().map {
                    UserDto(it[Users.id].value.toString(), it[Users.name], it[Users.description])
                }))
            }
        }
    }
}