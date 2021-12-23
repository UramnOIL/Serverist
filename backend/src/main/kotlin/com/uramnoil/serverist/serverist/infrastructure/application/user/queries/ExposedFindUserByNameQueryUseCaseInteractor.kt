package com.uramnoil.serverist.serverist.infrastructure.application.user.queries

import com.uramnoil.serverist.serverist.application.user.queries.FindUserByNameQueryUseCaseInputPort
import com.uramnoil.serverist.serverist.application.user.queries.FindUserByNameQueryUseCaseOutputPort
import com.uramnoil.serverist.serverist.infrastructure.Users
import com.uramnoil.serverist.serverist.infrastructure.toApplicationUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.Locale
import kotlin.coroutines.CoroutineContext

class ExposedFindUserByNameQueryUseCaseInteractor(
    private val outputPort: FindUserByNameQueryUseCaseOutputPort,
    coroutineContext: CoroutineContext
) : FindUserByNameQueryUseCaseInputPort, CoroutineScope by CoroutineScope(coroutineContext) {
    override fun execute(name: String, serversLimit: Long) {
        launch {
            val rowOrNull = runCatching {
                newSuspendedTransaction {
                    Users.select { Users.name.lowerCase() eq name.lowercase(Locale.getDefault()) }.firstOrNull()
                }
            }
            outputPort.handle(rowOrNull.map { it?.toApplicationUser() })
            return@launch
        }
    }
}
