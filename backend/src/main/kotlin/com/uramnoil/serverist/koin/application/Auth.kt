package com.uramnoil.serverist.koin.application

import com.uramnoil.serverist.auth.application.WithdrawUseCaseOutputPort
import com.uramnoil.serverist.auth.infrastructure.application.ActivateUseCaseInteractor
import com.uramnoil.serverist.auth.infrastructure.application.SignUpUseCaseInteractor
import com.uramnoil.serverist.auth.infrastructure.application.SingInUseCaseInteractor
import com.uramnoil.serverist.auth.infrastructure.application.SpringBootSendEmailService
import com.uramnoil.serverist.auth.infrastructure.application.WithdrawUseCaseInteractor
import com.uramnoil.serverist.auth.infrastructure.domain.kernel.service.HashPasswordServiceImpl
import com.uramnoil.serverist.presenter.AuthController
import com.uramnoil.serverist.presenter.UserController
import io.ktor.application.Application
import org.slf4j.Logger
import kotlin.coroutines.CoroutineContext
import com.uramnoil.serverist.domain.auth.authenticated.repositories.UserRepository as AuthenticatedUserRepository
import com.uramnoil.serverist.domain.auth.unauthenticated.repositories.UserRepository as UnauthenticatedUserRepository

fun Application.buildAuthController(
    logger: Logger,
    unauthenticatedRepository: UnauthenticatedUserRepository,
    authenticatedUserRepository: AuthenticatedUserRepository,
    userController: UserController
): AuthController {
    val hashPasswordService = HashPasswordServiceImpl()


    val sendEmailService = environment.config.run {
        SpringBootSendEmailService(
            property("mail.host").getString(),
            property("mail.port").getString().toInt(),
            property("mail.user").getString(),
            property("mail.password").getString(),
            property("mail.from").getString(),
            property("mail.activate_url").getString(),
        )
    }

    return AuthController(
        logger,
        { coroutineContext, outputPort ->
            SignUpUseCaseInteractor(
                sendEmailService,
                hashPasswordService,
                unauthenticatedRepository,
                coroutineContext,
                outputPort
            )
        },
        { coroutineContext, outputPort ->
            SingInUseCaseInteractor(hashPasswordService, coroutineContext, outputPort)
        },
        { coroutineContext, outputPort ->
            ActivateUseCaseInteractor(
                authenticatedUserRepository,
                unauthenticatedRepository,
                userController,
                coroutineContext,
                outputPort
            )
        },
        { coroutineContext: CoroutineContext, outputPort: WithdrawUseCaseOutputPort ->
            WithdrawUseCaseInteractor(authenticatedUserRepository, coroutineContext, outputPort)
        },
    )
}