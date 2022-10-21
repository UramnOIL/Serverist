package com.uramnoil.serverist.kodein.application

import com.uramnoil.serverist.application.server.commands.*
import com.uramnoil.serverist.application.server.queries.*
import com.uramnoil.serverist.application.server.services.ServerService
import com.uramnoil.serverist.domain.user.repositories.UserRepository
import com.uramnoil.serverist.domain.server.repositories.ServerRepository
import com.uramnoil.serverist.infrastracture.server.application.commands.DeleteServerCommandUseCaseInteractor
import com.uramnoil.serverist.infrastracture.server.application.commands.UpdateServerCommandUseCaseInteractor
import com.uramnoil.serverist.infrastracture.server.application.queries.ExposedFindAllServersQueryUseCaseInteractor
import com.uramnoil.serverist.infrastracture.server.application.queries.ExposedFindServersByOwnerQueryInteractor
import com.uramnoil.serverist.infrastracture.server.application.queries.FindServerByIdQueryUseCaseInteractor
import com.uramnoil.serverist.infrastracture.server.application.services.ExposedServerService
import com.uramnoil.serverist.infrastructure.server.application.commands.CreateServerCommandInteractor
import io.ktor.application.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.factory
import kotlin.coroutines.CoroutineContext

fun Application.buildServerApplicationDI(
    serverRepository: ServerRepository,
    userRepository: UserRepository,
    context: CoroutineContext
) = DI {
    bind<CreateServerCommandUseCaseInputPort>() with factory { outputPort: CreateServerCommandUseCaseOutputPort ->
        CreateServerCommandInteractor(outputPort, userRepository, serverRepository, context)
    }

    bind<DeleteServerCommandUseCaseInputPort>() with factory { outputPort: DeleteServerCommandUseCaseOutputPort ->
        DeleteServerCommandUseCaseInteractor(outputPort, serverRepository, context)
    }

    bind<UpdateServerCommandUseCaseInputPort>() with factory { outputPort: UpdateServerCommandUseCaseOutputPort ->
        UpdateServerCommandUseCaseInteractor(outputPort, serverRepository, context)
    }

    bind<FindServerByIdQueryUseCaseInputPort>() with factory { outputPort: FindServerByIdQueryUseCaseOutputPort ->
        FindServerByIdQueryUseCaseInteractor(outputPort, context)
    }

    bind<FindAllServersQueryUseCaseInputPort>() with factory { outputPort: FindAllServersQueryUseCaseOutputPort ->
        ExposedFindAllServersQueryUseCaseInteractor(outputPort, context)
    }

    bind<FindServersByOwnerQueryUseCaseInputPort>() with factory { outputPort: FindServersByOwnerQueryUseCaseOutputPort ->
        ExposedFindServersByOwnerQueryInteractor(outputPort, context)
    }

    bind<ServerService>() with factory {
        ExposedServerService()
    }
}