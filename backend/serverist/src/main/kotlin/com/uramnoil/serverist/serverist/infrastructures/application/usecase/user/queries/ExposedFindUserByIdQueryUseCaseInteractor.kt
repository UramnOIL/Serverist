package com.uramnoil.serverist.serverist.infrastructures.application.usecase.user.queries

import com.uramnoil.serverist.serverist.application.usecases.server.queries.UserDto
import com.uramnoil.serverist.serverist.application.usecases.user.queries.FindUserByIdQueryUseCaseInputPort
import com.uramnoil.serverist.serverist.infrastructures.application.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID
import kotlin.coroutines.CoroutineContext

class ExposedFindUserByIdQueryUseCaseInteractor(
) : FindUserByIdQueryUseCaseInputPort {
    override suspend fun execute(id: UUID): Result<UserDto?> {
        val rowOrNull = kotlin.runCatching {
            newSuspendedTransaction {
                Users.select { Users.id eq id }.firstOrNull()
            }
        }
        return rowOrNull.map { it?.toApplicationUser() }
    }
}
