package com.uramnoil.serverist.koin.application

import com.uramnoil.serverist.application.unauthenticated.commands.DeleteUserCommandUseCaseOutputPort
import com.uramnoil.serverist.auth.infrastructure.application.authenticated.queries.ExposedFindUserByEmailAndPasswordQueryInteractor
import com.uramnoil.serverist.auth.infrastructure.application.unauthenticated.commands.CreateUserCommandUseCaseInteractor
import com.uramnoil.serverist.auth.infrastructure.application.unauthenticated.queries.FindUserByActivationCodeQueryUseCaseInteractor
import com.uramnoil.serverist.auth.infrastructure.application.unauthenticated.queries.FindUserByEmailQueryUseCaseInteractor
import com.uramnoil.serverist.auth.infrastructure.application.unauthenticated.services.SpringBootSendEmailToAuthenticateServiceInputPort
import com.uramnoil.serverist.auth.infrastructure.domain.kernel.service.HashPasswordServiceImpl
import com.uramnoil.serverist.presenter.AuthController
import com.uramnoil.serverist.presenter.UserController
import io.ktor.application.*
import kotlin.coroutines.CoroutineContext
import com.uramnoil.serverist.auth.infrastructure.application.authenticated.commands.CreateUserCommandUseCaseInteractor as CreateAuthenticatedUserCommandUseCaseInteractor
import com.uramnoil.serverist.auth.infrastructure.application.authenticated.commands.DeleteUserCommandUseCaseInteractor as DeleteAuthenticatedUserCommandUseCaseInteractor
import com.uramnoil.serverist.auth.infrastructure.application.unauthenticated.commands.DeleteUserCommandUseCaseInteractor as DeleteUnauthenticatedUserCommandUseCaseInteractor
import com.uramnoil.serverist.domain.auth.authenticated.repositories.UserRepository as AuthenticatedUserRepository
import com.uramnoil.serverist.domain.auth.unauthenticated.repositories.UserRepository as UnauthenticatedUserRepository

fun Application.buildAuthController(
    unauthenticatedRepository: UnauthenticatedUserRepository,
    authenticatedUserRepository: AuthenticatedUserRepository,
    userController: UserController
): AuthController {
    val hashPasswordService = HashPasswordServiceImpl()
    return AuthController(
        createUnauthenticatedUserCommandUseCaseInputPortFactory = { coroutineContext, outputPort ->
            CreateUserCommandUseCaseInteractor(
                unauthenticatedRepository,
                hashPasswordService,
                outputPort,
                coroutineContext
            )
        },
        deleteUnauthenticatedUserCommandUseCaseInputPortFactory = { coroutineContext: CoroutineContext, outputPort: DeleteUserCommandUseCaseOutputPort ->
            DeleteUnauthenticatedUserCommandUseCaseInteractor(
                unauthenticatedRepository,
                outputPort,
                coroutineContext
            )
        },
        sendEmailToAuthenticateUseCaseInputPortFactory = { coroutineContext, outputPort ->
            environment.config.run {
                SpringBootSendEmailToAuthenticateServiceInputPort(
                    property("mail.host").getString(),
                    property("mail.port").getString().toInt(),
                    property("mail.user").getString(),
                    property("mail.password").getString(),
                    property("mail.from").getString(),
                    property("mail.activate_url").getString(),
                    outputPort,
                    coroutineContext
                )
            }
        },
        findUserByEmailQueryUseCaseInputPortFactory = { coroutineContext, outputPort ->
            FindUserByEmailQueryUseCaseInteractor(outputPort, coroutineContext)
        },
        findUserByActivationCodeQueryUseCaseInputPortFactory = { coroutineContext, outputPort ->
            FindUserByActivationCodeQueryUseCaseInteractor(outputPort, coroutineContext)
        },
        findUserByEmailAndPasswordQueryUseCaseInputPortFactory = { coroutineContext, outputPort ->
            ExposedFindUserByEmailAndPasswordQueryInteractor(hashPasswordService, outputPort, coroutineContext)
        },
        createAuthenticatedCommandUserCaseInputPortFactory = { coroutineContext, outputPort ->
            CreateAuthenticatedUserCommandUseCaseInteractor(
                authenticatedUserRepository,
                hashPasswordService,
                outputPort,
                coroutineContext
            )
        },
        deleteAuthenticatedUserCommandUseCaseInputPortFactory = { coroutineContext, outputPort ->
            DeleteAuthenticatedUserCommandUseCaseInteractor(authenticatedUserRepository, outputPort, coroutineContext)
        },
        userController = userController
    )
}