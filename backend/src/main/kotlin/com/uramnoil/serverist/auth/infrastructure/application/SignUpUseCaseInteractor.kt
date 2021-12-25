package com.uramnoil.serverist.auth.infrastructure.application

import com.uramnoil.serverist.auth.application.SendEmailService
import com.uramnoil.serverist.auth.application.SignUpUseCaseInputPort
import com.uramnoil.serverist.auth.application.SignUpUseCaseOutputPort
import com.uramnoil.serverist.auth.infrastructure.AuthenticatedUsers
import com.uramnoil.serverist.auth.infrastructure.UnauthenticatedUsers
import com.uramnoil.serverist.domain.auth.kernel.model.Email
import com.uramnoil.serverist.domain.auth.kernel.model.Password
import com.uramnoil.serverist.domain.auth.kernel.services.HashPasswordService
import com.uramnoil.serverist.domain.auth.unauthenticated.models.ActivationCode
import com.uramnoil.serverist.domain.auth.unauthenticated.models.Id
import com.uramnoil.serverist.domain.auth.unauthenticated.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID
import kotlin.coroutines.CoroutineContext
import com.uramnoil.serverist.domain.auth.unauthenticated.repositories.UserRepository as UnathenticatedUserRepository

class SignUpUseCaseInteractor(
    private val sendEmailService: SendEmailService,
    private val hashPasswordService: HashPasswordService,
    private val unauthenticatedUserRepository: UnathenticatedUserRepository,
    coroutineContext: CoroutineContext,
    private val outputPort: SignUpUseCaseOutputPort
) : SignUpUseCaseInputPort, CoroutineScope by CoroutineScope(coroutineContext) {
    override fun execute(email: String, password: String, activationCode: UUID) {
        launch {
            val result = runCatching {
                val row = newSuspendedTransaction {
                    AuthenticatedUsers.select { UnauthenticatedUsers.email eq email }.firstOrNull()
                }

                if (row != null) throw IllegalArgumentException("This email is already used.")

                val newPassword = Password(password)

                val hashedPassword = hashPasswordService.hash(newPassword)

                val newResult = User.new(
                    id = Id(UUID.randomUUID()),
                    email = Email(email),
                    hashedPassword = hashedPassword,
                    activationCode = ActivationCode(activationCode),
                )

                val newUser = newResult.getOrThrow()

                sendEmailService.sendActivationEmail(email, activationCode).getOrThrow()

                unauthenticatedUserRepository.insert(newUser).getOrThrow()
            }

            outputPort.handle(result)
        }
    }
}
