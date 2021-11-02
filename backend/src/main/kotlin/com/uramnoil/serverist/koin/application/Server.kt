package com.uramnoil.serverist.koin.application

import com.uramnoil.serverist.application.server.commands.CreateServerCommandUseCaseInputPort
import com.uramnoil.serverist.application.server.commands.DeleteServerCommandUseCaseInputPort
import com.uramnoil.serverist.application.server.commands.UpdateServerCommandUseCaseInputPort
import com.uramnoil.serverist.application.server.queries.FindAllServersQueryUseCaseInputPort
import com.uramnoil.serverist.application.server.queries.FindServerByIdQueryUseCaseInputPort
import com.uramnoil.serverist.application.server.queries.FindServersByOwnerQueryUseCaseInputPort
import com.uramnoil.serverist.application.server.services.ServerService
import com.uramnoil.serverist.domain.server.repositories.ServerRepository
import com.uramnoil.serverist.domain.user.repositories.UserRepository
import com.uramnoil.serverist.infrastracture.server.application.services.ExposedServerService
import com.uramnoil.serverist.infrastructure.server.application.commands.CreateServerCommandInteractor
import com.uramnoil.serverist.infrastructure.server.application.commands.DeleteServerCommandUseCaseInteractor
import com.uramnoil.serverist.infrastructure.server.application.commands.UpdateServerCommandUseCaseInteractor
import com.uramnoil.serverist.infrastructure.server.application.queries.ExposedFindAllServersQueryUseCaseInteractor
import com.uramnoil.serverist.infrastructure.server.application.queries.ExposedFindServersByOwnerQueryInteractor
import com.uramnoil.serverist.infrastructure.server.application.queries.FindServerByIdQueryUseCaseInteractor
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