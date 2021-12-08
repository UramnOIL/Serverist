package com.uramnoil.serverist.auth.infrastructure.application

import com.uramnoil.serverist.auth.application.SignInUseCaseOutputPort
import com.uramnoil.serverist.auth.application.SingInUseCaseInputPort
import com.uramnoil.serverist.auth.infrastructure.AuthenticatedUsers
import com.uramnoil.serverist.auth.infrastructure.toApplicationAuthenticatedUser
import com.uramnoil.serverist.domain.auth.kernel.model.HashedPassword
import com.uramnoil.serverist.domain.auth.kernel.model.Password
import com.uramnoil.serverist.domain.auth.kernel.services.HashPasswordService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.coroutines.CoroutineContext

class SingInUseCaseInteractor(
    private val hashPasswordService: HashPasswordService,
    coroutineContext: CoroutineContext,
    private val outputPort: SignInUseCaseOutputPort,
) : SingInUseCaseInputPort, CoroutineScope by CoroutineScope(coroutineContext) {
    override fun execute(email: String, password: String) {
        launch {
            val rowResult = kotlin.runCatching {
                newSuspendedTransaction {
                    AuthenticatedUsers.select { (AuthenticatedUsers.email eq email) }.firstOrNull()
                }
            }

            val row = rowResult.getOrElse {
                outputPort.handle(Result.failure(it))
                return@launch
            }

            // not existing
            row ?: run {
                outputPort.handle(Result.failure(IllegalArgumentException("Invalid email or password")))
                return@launch
            }

            val user = row.toApplicationAuthenticatedUser()

            // check password
            val isSamePassword = hashPasswordService.check(Password(password), HashedPassword(user.hashedPassword))

            if (!isSamePassword) {
                outputPort.handle(Result.failure(IllegalArgumentException("Invalid email or password")))
            }
            outputPort.handle(Result.success(user.id))
        }
    }
}