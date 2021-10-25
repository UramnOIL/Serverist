package com.uramnoil.serverist.infrastracture.user

import com.uramnoil.serverist.application.user.queries.FindUserByNameQueryInputPort
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*

class ExposedFindUserByNameQueryInteractor : FindUserByNameQueryInputPort {
    override suspend fun execute(name: String, serversLimit: Long) = kotlin.runCatching {
        newSuspendedTransaction {
            Users.select { Users.name.lowerCase() eq name.lowercase(Locale.getDefault()) }.firstOrNull()
        }?.let(ResultRow::toApplicationUser)
    }
}