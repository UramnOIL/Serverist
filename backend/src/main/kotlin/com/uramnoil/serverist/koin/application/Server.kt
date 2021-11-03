package com.uramnoil.serverist.koin.application

import com.uramnoil.serverist.domain.server.repositories.ServerRepository
import com.uramnoil.serverist.domain.user.repositories.UserRepository
import com.uramnoil.serverist.server.application.commands.CreateServerCommandUseCaseInputPort
import com.uramnoil.serverist.server.application.commands.DeleteServerCommandUseCaseInputPort
import com.uramnoil.serverist.server.application.commands.UpdateServerCommandUseCaseInputPort
import com.uramnoil.serverist.server.application.queries.FindAllServersQueryUseCaseInputPort
import com.uramnoil.serverist.server.application.queries.FindServerByIdQueryUseCaseInputPort
import com.uramnoil.serverist.server.application.queries.FindServersByOwnerQueryUseCaseInputPort
import com.uramnoil.serverist.server.application.services.ServerService
import com.uramnoil.serverist.server.infrastructure.application.commands.CreateServerCommandInteractor
import com.uramnoil.serverist.server.infrastructure.application.commands.DeleteServerCommandUseCaseInteractor
import com.uramnoil.serverist.server.infrastructure.application.commands.UpdateServerCommandUseCaseInteractor
import com.uramnoil.serverist.server.infrastructure.application.queries.ExposedFindAllServersQueryUseCaseInteractor
import com.uramnoil.serverist.server.infrastructure.application.queries.ExposedFindServersByOwnerQueryInteractor
import com.uramnoil.serverist.server.infrastructure.application.queries.FindServerByIdQueryUseCaseInteractor
import com.uramnoil.serverist.server.infrastructure.application.services.ExposedServerService
import io.ktor.application.*
import org.koin.dsl.module

fun Application.buildServerApplicationDI(
    serverRepository: ServerRepository,
    userRepository: UserRepository,
) = module {
    single<CreateServerCommandUseCaseInputPort> {
        CreateServerCommandInteractor(userRepository, serverRepository)
    }

    single<DeleteServerCommandUseCaseInputPort> {
        DeleteServerCommandUseCaseInteractor(serverRepository)
    }

    single<UpdateServerCommandUseCaseInputPort> {
        UpdateServerCommandUseCaseInteractor(serverRepository)
    }

    single<FindServerByIdQueryUseCaseInputPort> {
        FindServerByIdQueryUseCaseInteractor()
    }

    single<FindAllServersQueryUseCaseInputPort> {
        ExposedFindAllServersQueryUseCaseInteractor()
    }

    single<FindServersByOwnerQueryUseCaseInputPort> {
        ExposedFindServersByOwnerQueryInteractor()
    }

    single<ServerService> {
        ExposedServerService()
    }
}