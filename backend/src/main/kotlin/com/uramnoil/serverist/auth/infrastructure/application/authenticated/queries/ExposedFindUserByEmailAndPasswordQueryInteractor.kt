package com.uramnoil.serverist.auth.infrastructure.application.authenticated.queries

import com.uramnoil.serverist.application.authenticated.queries.FindUserByEmailAndPasswordQueryUseCaseInputPort
import com.uramnoil.serverist.application.authenticated.queries.FindUserByEmailAndPasswordQueryUseCaseOutputPort
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

class ExposedFindUserByEmailAndPasswordQueryInteractor(
    private val hashPasswordService: HashPasswordService,
    private val outputPort: FindUserByEmailAndPasswordQueryUseCaseOutputPort,
    private val coroutineContext: CoroutineContext
) : FindUserByEmailAndPasswordQueryUseCaseInputPort {
    override fun execute(mail: String, password: String) {
        CoroutineScope(coroutineContext).launch {
            val rowResult = kotlin.runCatching {
                newSuspendedTransaction {
                    AuthenticatedUsers.select { (AuthenticatedUsers.email eq mail) }.firstOrNull()
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
            return@launch
        }
    }
}