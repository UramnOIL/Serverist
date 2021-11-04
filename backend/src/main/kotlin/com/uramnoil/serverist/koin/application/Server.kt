package com.uramnoil.serverist.koin.application

import com.uramnoil.serverist.domain.server.repositories.ServerRepository
import com.uramnoil.serverist.domain.user.repositories.UserRepository
import com.uramnoil.serverist.serverist.server.infrastructure.application.commands.CreateServerCommandInteractor
import com.uramnoil.serverist.serverist.server.infrastructure.application.commands.DeleteServerCommandUseCaseInteractor
import com.uramnoil.serverist.serverist.server.infrastructure.application.commands.UpdateServerCommandUseCaseInteractor
import com.uramnoil.serverist.serverist.server.infrastructure.application.queries.ExposedFindAllServersQueryUseCaseInteractor
import com.uramnoil.serverist.serverist.server.infrastructure.application.queries.ExposedFindServersByOwnerQueryInteractor
import com.uramnoil.serverist.serverist.server.infrastructure.application.queries.FindServerByIdQueryUseCaseInteractor
import com.uramnoil.serverist.serverist.server.infrastructure.application.services.ExposedServerService
import io.ktor.application.*
import org.koin.dsl.module

fun Application.buildServerDI(
    serverRepository: ServerRepository,
    userRepository: UserRepository,
) = module {
    val createServerCommand = CreateServerCommandInteractor(userRepository, serverRepository)
    val deleteServerCommand = DeleteServerCommandUseCaseInteractor(serverRepository)
    val updateServerCommandUseCaseInputPort = UpdateServerCommandUseCaseInteractor(serverRepository)
    val findServerByIdQueryUseCaseInputPort = FindServerByIdQueryUseCaseInteractor()
    val findAllServersQueryUseCaseInputPort = ExposedFindAllServersQueryUseCaseInteractor()
    val findServersByOwnerQueryUseCaseInputPort = ExposedFindServersByOwnerQueryInteractor()
    val serverService = ExposedServerService()
}