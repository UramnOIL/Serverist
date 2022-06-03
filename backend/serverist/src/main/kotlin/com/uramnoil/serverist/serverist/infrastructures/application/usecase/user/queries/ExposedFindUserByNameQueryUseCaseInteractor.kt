package com.uramnoil.serverist.serverist.infrastructures.application.usecase.user.queries

import com.uramnoil.serverist.serverist.application.usecases.user.queries.FindUserByNameQueryUseCaseInputPort
import com.uramnoil.serverist.serverist.infrastructures.application.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.Locale
import kotlin.coroutines.CoroutineContext

class ExposedFindUserByNameQueryUseCaseInteractor(
) : FindUserByNameQueryUseCaseInputPort {
    override suspend fun execute(name: String, serversLimit: Long) {
            val rowOrNull = runCatching {
                newSuspendedTransaction {
                    Users.select { Users.name.lowerCase() eq name.lowercase(Locale.getDefault()) }.firstOrNull()
                }
            }
            return rowOrNull.map { it?.toApplicationUser() }
        }
    }
}
