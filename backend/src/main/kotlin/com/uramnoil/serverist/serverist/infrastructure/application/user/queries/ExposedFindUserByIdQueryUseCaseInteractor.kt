package com.uramnoil.serverist.serverist.infrastructure.application.user.queries

import com.uramnoil.serverist.serverist.application.user.queries.FindUserByIdQueryUseCaseInputPort
import com.uramnoil.serverist.serverist.application.user.queries.FindUserByIdQueryUseCaseOutputPort
import com.uramnoil.serverist.serverist.infrastructure.Users
import com.uramnoil.serverist.serverist.infrastructure.toApplicationUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*
import kotlin.coroutines.CoroutineContext

class ExposedFindUserByIdQueryUseCaseInteractor(
    private val outputPort: FindUserByIdQueryUseCaseOutputPort,
    private val coroutineContext: CoroutineContext
) : FindUserByIdQueryUseCaseInputPort {
    override fun execute(id: UUID) {
        CoroutineScope(coroutineContext).launch {
            val rowOrNull = kotlin.runCatching {
                newSuspendedTransaction {
                    Users.select { Users.id eq id }.firstOrNull()
                }
            }
            outputPort.handle(rowOrNull.map { it?.toApplicationUser() })
            return@launch
        }
    }
}