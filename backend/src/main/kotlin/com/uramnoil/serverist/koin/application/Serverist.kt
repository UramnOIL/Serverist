package com.uramnoil.serverist.koin.application

import com.uramnoil.serverist.domain.serverist.repositories.ServerRepository
import com.uramnoil.serverist.domain.serverist.repositories.UserRepository
import com.uramnoil.serverist.presenter.ServerController
import com.uramnoil.serverist.presenter.UserController
import com.uramnoil.serverist.serverist.infrastructure.application.server.commands.CreateServerCommandInteractor
import com.uramnoil.serverist.serverist.infrastructure.application.server.commands.DeleteServerCommandUseCaseInteractor
import com.uramnoil.serverist.serverist.infrastructure.application.server.commands.UpdateServerCommandUseCaseInteractor
import com.uramnoil.serverist.serverist.infrastructure.application.server.queries.ExposedFindAllServersQueryUseCaseInteractor
import com.uramnoil.serverist.serverist.infrastructure.application.server.queries.ExposedFindServersByOwnerQueryInteractor
import com.uramnoil.serverist.serverist.infrastructure.application.server.queries.FindServerByIdQueryUseCaseInteractor
import com.uramnoil.serverist.serverist.infrastructure.application.server.services.ExposedServerService
import com.uramnoil.serverist.serverist.infrastructure.application.user.command.CreateUserCommandUseCaseInteractor
import com.uramnoil.serverist.serverist.infrastructure.application.user.command.DeleteUserCommandUseCaseInteractor
import com.uramnoil.serverist.serverist.infrastructure.application.user.command.UpdateUserCommandUseCaseInteractorForServer
import com.uramnoil.serverist.serverist.infrastructure.application.user.queries.FindUserByIdQueryUseCaseInteractor
import io.ktor.application.*

data class Controllers(val userController: UserController, val serverController: ServerController)

fun Application.buildServeristControllers(
    userRepository: UserRepository,
    serverRepository: ServerRepository
): Controllers {
    val serverController = ServerController(
        service = ExposedServerService(),
        createServerCommandUserCaseInputPortFactory = { coroutineContext, outputPort ->
            CreateServerCommandInteractor(userRepository, serverRepository, outputPort, coroutineContext)
        },
        updateServerCommandUserCaseInputPortFactory = { coroutineContext, outputPort ->
            UpdateServerCommandUseCaseInteractor(serverRepository, outputPort, coroutineContext)
        },
        deleteServerCommandUserCaseInputPortFactory = { coroutineContext, outputPort ->
            DeleteServerCommandUseCaseInteractor(serverRepository, outputPort, coroutineContext)
        },
        findByOwnerServerQueryUserCaseInputPortFactory = { coroutineContext, outputPort ->
            ExposedFindServersByOwnerQueryInteractor(outputPort, coroutineContext)
        },
        findByIdServerQueryUserCaseInputPortFactory = { coroutineContext, outputPort ->
            FindServerByIdQueryUseCaseInteractor(outputPort, coroutineContext)
        },
        findAllServerQueryUserCaseInputPortFactory = { coroutineContext, outputPort ->
            ExposedFindAllServersQueryUseCaseInteractor(outputPort, coroutineContext)
        }
    )

    val userController = UserController(
        createUserCommandUseCaseInputPortFactory = { coroutineContext, outputPort ->
            CreateUserCommandUseCaseInteractor(userRepository, outputPort, coroutineContext)
        },
        updateUserCommandUseCaseInputPortFactory = { coroutineContext, outputPort ->
            UpdateUserCommandUseCaseInteractorForServer(userRepository, outputPort, coroutineContext)
        },
        deleteUserCommandUseCaseInputPortFactory = { coroutineContext, outputPort ->
            DeleteUserCommandUseCaseInteractor(userRepository, outputPort, coroutineContext)
        },
        findUserByIdQueryUseCaseInputPortFactory = { coroutineContext, outputPort ->
            FindUserByIdQueryUseCaseInteractor(userRepository, outputPort, coroutineContext)
        },
    )

    return Controllers(userController, serverController)
}