package com.uramnoil.serverist.serverist.infrastructures.application.usecase.user.queries

import com.uramnoil.serverist.serverist.application.usecases.user.queries.FindAllUsersQueryUseCaseInputPort
import com.uramnoil.serverist.serverist.infrastructures.application.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.coroutines.CoroutineContext

class ExposedFindAllUsersQueryUseCaseInteractor() : FindAllUsersQueryUseCaseInputPort {
    override suspend fun execute() {
            val result = runCatching {
                newSuspendedTransaction {
                    Users.selectAll().map { it.toApplicationUser() }
                }
            }
            return result
        }
    }
}
