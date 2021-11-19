package com.uramnoil.serverist.serverist.infrastructure.application.user.queries

import com.uramnoil.serverist.application.user.queries.FindAllUsersQueryUseCaseInputPort
import com.uramnoil.serverist.application.user.queries.FindAllUsersQueryUseCaseOutputPort
import com.uramnoil.serverist.serverist.infrastructure.Users
import com.uramnoil.serverist.serverist.infrastructure.toApplicationUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.coroutines.CoroutineContext

class ExposedFindAllUsersQueryUseCaseInteractor(
    private val outputPort: FindAllUsersQueryUseCaseOutputPort,
    private val coroutineContext: CoroutineContext
) : FindAllUsersQueryUseCaseInputPort {
    override fun execute() {
        CoroutineScope(coroutineContext).launch {
            val result = runCatching {
                newSuspendedTransaction {
                    Users.selectAll().map { it.toApplicationUser() }
                }
            }
            outputPort.handle(result)
            return@launch
        }
    }
}