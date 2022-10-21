package com.uramnoil.serverist.infrastracture.user.queries

import com.uramnoil.serverist.application.user.queries.FindUserByNameQueryUseCaseInputPort
import com.uramnoil.serverist.application.user.queries.FindUserByNameQueryUseCaseOutputPort
import com.uramnoil.serverist.infrastructure.user.Users
import com.uramnoil.serverist.infrastracture.user.toApplicationUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*
import kotlin.coroutines.CoroutineContext

class ExposedFindUserByNameQueryUseCaseInteractor(
    private val outputPort: FindUserByNameQueryUseCaseOutputPort,
    parentContext: CoroutineContext
) :
    FindUserByNameQueryUseCaseInputPort,
    CoroutineScope by CoroutineScope(parentContext + Job(parentContext.job)) {
    override fun execute(name: String, serversLimit: Long) {
        launch {
            val rowOrNull = runCatching {
                newSuspendedTransaction {
                    Users.select { Users.name.lowerCase() eq name.lowercase(Locale.getDefault()) }.firstOrNull()
                }
            }
            val result = rowOrNull.map { it?.toApplicationUser() }
            outputPort.handle(result)
        }
    }
}