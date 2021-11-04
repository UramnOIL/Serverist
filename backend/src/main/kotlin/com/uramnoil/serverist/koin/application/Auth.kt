package com.uramnoil.serverist.koin.application

import com.uramnoil.serverist.auth.application.unauthenticated.service.SendEmailToAuthenticateUseCase
import com.uramnoil.serverist.auth.infrastructure.HashPasswordServiceImpl
import com.uramnoil.serverist.auth.infrastructure.authenticated.domain.ExposedUserRepositoryImpl
import com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.commands.CreateUserCommandUseCaseInteractor
import com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.queries.FindUserByActivationCodeQueryUseCaseInteractor
import com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.queries.FindUserByEmailQueryUseCaseInteractor
import com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.queries.FindUserByIdQueryUseCaseInteractor
import com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.services.SpringBootSendEmailToAuthenticateService
import com.uramnoil.serverist.auth.infrastructure.unauthenticated.domain.repositories.ExposedUserRepository
import com.uramnoil.serverist.domain.auth.unauthenticated.repositories.UserRepository
import com.uramnoil.serverist.presenter.AuthController
import com.uramnoil.serverist.presenter.UserController
import com.uramnoil.serverist.serverist.user.infrastructure.application.queries.ExposedFindUserByEmailAndPasswordQueryInteractor
import io.ktor.application.*
import com.uramnoil.serverist.auth.infrastructure.authenticated.application.commands.CreateUserCommandUseCaseInteractor as CreateAuthenticatedUserCommandUseCaseInteractor
import com.uramnoil.serverist.auth.infrastructure.authenticated.application.commands.DeleteUserCommandUseCaseInteractor as DeleteAuthenticatedUserCommandUseCaseInteractor
import com.uramnoil.serverist.auth.infrastructure.unauthenticated.application.commands.DeleteUserCommandUseCaseInteractor as DeleteUnauthenticatedUserCommandUseCaseInteractor1

fun Application.buildAuthController(userController: UserController): AuthController {
    val hashPasswordService = HashPasswordServiceImpl()

    val unauthenticatedUserRepository: UserRepository = ExposedUserRepository()

    val createUnauthenticatedUserCommandInputPort =
        CreateUserCommandUseCaseInteractor(unauthenticatedUserRepository, hashPasswordService)
    val deleteUnauthenticatedUserCommandInputPort =
        DeleteUnauthenticatedUserCommandUseCaseInteractor1(unauthenticatedUserRepository)
    val findUserByIdQueryUseCaseInputPort = FindUserByIdQueryUseCaseInteractor(unauthenticatedUserRepository)
    val findUserByActivationCodeQueryUseCaseInputPort = FindUserByActivationCodeQueryUseCaseInteractor()
    val findUserByEmailQueryUseCaseInputPort = FindUserByEmailQueryUseCaseInteractor()
    val findUserByEmailAndPasswordQueryUseCaseInputPort =
        ExposedFindUserByEmailAndPasswordQueryInteractor(hashPasswordService)

    val sendEmailToAuthenticateUseCase: SendEmailToAuthenticateUseCase = environment.config.run {
        SpringBootSendEmailToAuthenticateService(
            property("mail.host").getString(),
            property("mail.port").getString().toInt(),
            property("mail.username").getString(),
            property("mail.password").getString(),
            property("mail.from").getString(),
            property("mail.auth_url").getString()
        )
    }

    val authenticatedRepository = ExposedUserRepositoryImpl()

    val createAuthenticatedUserCommandInputPort =
        CreateAuthenticatedUserCommandUseCaseInteractor(authenticatedRepository, hashPasswordService)
    val deleteAuthenticatedUserCommandInputPort =
        DeleteAuthenticatedUserCommandUseCaseInteractor(authenticatedRepository)

    return AuthController(
        createUnauthenticatedUserCommandInputPort,
        deleteUnauthenticatedUserCommandInputPort,
        sendEmailToAuthenticateUseCase = sendEmailToAuthenticateUseCase,
        findUserByEmailQueryUseCaseInputPort = findUserByEmailQueryUseCaseInputPort,
        findUserByActivationCodeQueryUseCaseInputPort = findUserByActivationCodeQueryUseCaseInputPort,
        findUserByEmailAndPasswordQueryUseCaseInputPort = findUserByEmailAndPasswordQueryUseCaseInputPort,
        createAuthenticatedCommandUserCaseInputPort = createAuthenticatedUserCommandInputPort,
        deleteAuthenticatedUserCommandUseCaseInputPort = deleteAuthenticatedUserCommandInputPort,
        userController = userController
    )
}