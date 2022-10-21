package com.uramnoil.serverist.infrastructure.user.queries

import com.mysql.cj.protocol.x.ReusableInputStream
import com.uramnoil.serverist.application.auth.authenticated.TryLoginUseCaseInputPort
import com.uramnoil.serverist.application.auth.authenticated.TryLoginUseCaseOutputPort
import com.uramnoil.serverist.domain.auth.kernel.model.HashedPassword
import com.uramnoil.serverist.domain.auth.kernel.model.Password
import com.uramnoil.serverist.infrastracture.auth.authenticated.Users
import com.uramnoil.serverist.infrastructure.auth.authenticated.toApplicationAuthenticatedUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.coroutines.CoroutineContext

class ExposedTryLoginInteractor(
    private val outputPort: TryLoginUseCaseOutputPort,
    private val hashPasswordService: com.uramnoil.serverist.domain.auth.kernel.services.HashPasswordService,
    parentContext: CoroutineContext,
) :
    TryLoginUseCaseInputPort,
    CoroutineScope by CoroutineScope(parentContext + Job(parentContext.job)) {
    override fun execute(accountIdOrEmail: String, password: String) {
        launch {
            val rowResult = kotlin.runCatching {
                newSuspendedTransaction {
                    Users.select { (Users.email eq accountIdOrEmail) }.firstOrNull()
                }
            }

            val row = rowResult.getOrElse {
                outputPort.handle(Result.failure(it))
                return@launch
            }

            // not existing
            row ?: run {
                outputPort.handle(Result.failure(IllegalArgumentException("Could not find user which matches id or email.")))
                return@launch
            }

            val user = row.toApplicationAuthenticatedUser()

            // check password
            val isSamePassword = hashPasswordService.check(Password(password), HashedPassword(user.hashedPassword))

            val idOrNull = if (isSamePassword) user.id else null
            outputPort.handle(Result.success(idOrNull))
        }
    }
}