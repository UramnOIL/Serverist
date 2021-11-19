package com.uramnoil.serverist.serverist.infrastructure.application.user.queries

import com.uramnoil.serverist.application.user.queries.FindUserByNameQueryUseCaseInputPort
import com.uramnoil.serverist.application.user.queries.FindUserByNameQueryUseCaseOutputPort
import com.uramnoil.serverist.serverist.infrastructure.Users
import com.uramnoil.serverist.serverist.infrastructure.toApplicationUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*
import kotlin.coroutines.CoroutineContext

class ExposedFindUserByNameQueryUseCaseInteractor(
    private val outputPort: FindUserByNameQueryUseCaseOutputPort,
    private val coroutineContext: CoroutineContext
) : FindUserByNameQueryUseCaseInputPort {
    override fun execute(name: String, serversLimit: Long) {
        CoroutineScope(coroutineContext).launch {
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