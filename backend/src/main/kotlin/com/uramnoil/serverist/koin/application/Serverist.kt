package com.uramnoil.serverist.koin.application

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
import com.uramnoil.serverist.serverist.infrastructure.application.user.command.UpdateUserCommandUseCaseInteractor
import com.uramnoil.serverist.serverist.infrastructure.application.user.queries.ExposedFindAllUsersQueryUseCaseInteractor
import com.uramnoil.serverist.serverist.infrastructure.application.user.queries.ExposedFindUserByAccountIdQueryUseCaseInteractor
import com.uramnoil.serverist.serverist.infrastructure.application.user.queries.ExposedFindUserByNameQueryUseCaseInteractor
import com.uramnoil.serverist.serverist.infrastructure.application.user.queries.FindUserByIdQueryUseCaseInteractor
import com.uramnoil.serverist.serverist.infrastructure.repositories.ExposedServerRepository
import com.uramnoil.serverist.serverist.infrastructure.repositories.ExposedUserRepository
import io.ktor.application.*

data class Controllers(val userController: UserController, val serverController: ServerController)

fun Application.buildServeristControllers(): Controllers {
    val userRepository = ExposedUserRepository()
    val serverRepository = ExposedServerRepository()
    val createServerCommand = CreateServerCommandInteractor(userRepository, serverRepository)
    val deleteServerCommand = DeleteServerCommandUseCaseInteractor(serverRepository)
    val updateServerCommandUseCaseInputPort = UpdateServerCommandUseCaseInteractor(serverRepository)
    val findServerByIdQueryUseCaseInputPort = FindServerByIdQueryUseCaseInteractor()
    val findAllServersQueryUseCaseInputPort = ExposedFindAllServersQueryUseCaseInteractor()
    val findServersByOwnerQueryUseCaseInputPort = ExposedFindServersByOwnerQueryInteractor()
    val serverService = ExposedServerService()

    val serverController = ServerController(
        service = serverService,
        createCommand = createServerCommand,
        updateCommand = updateServerCommandUseCaseInputPort,
        deleteCommand = deleteServerCommand,
        findAllQuery = findAllServersQueryUseCaseInputPort,
        findByIdQuery = findServerByIdQueryUseCaseInputPort,
        findByOwnerQuery = findServersByOwnerQueryUseCaseInputPort
    )

    val createUserCommandUseCaseInputPort = CreateUserCommandUseCaseInteractor(userRepository)
    val deleteUserCommandUseCaseInputPort = DeleteUserCommandUseCaseInteractor(userRepository)
    val updateUserCommandUseCaseInputPort = UpdateUserCommandUseCaseInteractor(userRepository)
    val findUserByIdQueryUseCaseInputPort = FindUserByIdQueryUseCaseInteractor(userRepository)
    val fndAllUsersQueryUseCaseInputPort = ExposedFindAllUsersQueryUseCaseInteractor()
    val findUserByAccountIdQueryUseCaseInputPort = ExposedFindUserByAccountIdQueryUseCaseInteractor()
    val findUserByNameQueryUseCaseInputPort = ExposedFindUserByNameQueryUseCaseInteractor()

    val userController = UserController(
        createUserCommandUseCaseInputPort = createUserCommandUseCaseInputPort,
        updateUserCommandUseCaseInputPort = updateUserCommandUseCaseInputPort,
        deleteUserCommandUseCaseInputPort = deleteUserCommandUseCaseInputPort,
        findUserByIdQueryUseCaseInputPort = findUserByIdQueryUseCaseInputPort
    )

    return Controllers(userController, serverController)
}