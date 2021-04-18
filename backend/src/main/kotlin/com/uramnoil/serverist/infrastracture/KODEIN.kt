package com.uramnoil.serverist.infrastracture

import com.uramnoil.serverist.application.service.usecases.server.commands.CreateServerCommand
import com.uramnoil.serverist.application.service.usecases.server.commands.DeleteServerCommand
import com.uramnoil.serverist.application.service.usecases.server.commands.UpdateServerCommand
import com.uramnoil.serverist.application.service.usecases.server.queries.FindServerByIdOutputPort
import com.uramnoil.serverist.application.service.usecases.server.queries.FindServerByIdQuery
import com.uramnoil.serverist.application.service.usecases.user.commands.CreateUserCommand
import com.uramnoil.serverist.application.service.usecases.user.commands.DeleteUserCommand
import com.uramnoil.serverist.application.service.usecases.user.commands.UpdateUserCommand
import com.uramnoil.serverist.application.service.usecases.user.queries.FindAllUsersOutputPort
import com.uramnoil.serverist.application.service.usecases.user.queries.FindAllUsersQuery
import com.uramnoil.serverist.application.service.usecases.user.queries.FindUserByNameOutputPort
import com.uramnoil.serverist.application.service.usecases.user.queries.FindUserByNameQuery
import com.uramnoil.serverist.domain.service.repositories.ServerRepository
import com.uramnoil.serverist.domain.service.repositories.UserRepository
import com.uramnoil.serverist.domain.service.services.server.CreateServerService
import com.uramnoil.serverist.domain.service.services.user.CreateUserService
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.exposed.sql.Database
import org.kodein.di.*
import kotlin.coroutines.CoroutineContext

fun buildDi(database: Database, context: CoroutineContext) = DI {
    val scope = CoroutineScope(context)

    bind<ServerRepository>() with singleton {
        ExposedServerRepository(database, context)
    }

    bind<UserRepository>() with singleton {
        ExposedUserRepository(database, context)
    }

    bind<CreateServerService>() with singleton {
        ExposedCreateServerService(database, context)
    }

    bind<CreateUserService>() with singleton {
        ExposedCreateUserService(database, context)
    }

    bind<CreateServerCommand>() with factory {
        ExposedCreateServerCommand(database, instance(), instance(), context)
    }

    bind<DeleteServerCommand>() with factory {
        ExposedDeleteServerCommand(database, instance(), context)
    }

    bind<UpdateServerCommand>() with factory {
        ExposedUpdateServerCommand(database, instance(), context)
    }

    bind<FindServerByIdQuery>() with factory { outputPort: FindServerByIdOutputPort ->
        ExposedFindServerByIdQuery(database, instance(), outputPort, context)
    }

    // <--- User --->

    bind<CreateUserCommand>() with factory {
        ExposedCreateUserCommand(instance(), context)
    }

    bind<DeleteUserCommand>() with factory {
        ExposedDeleteUserCommand(instance(), context)
    }

    bind<UpdateUserCommand>() with factory {
        ExposedUpdateUserCommand(database, instance(), context)
    }

    bind<FindAllUsersQuery>() with factory { outputPort: FindAllUsersOutputPort ->
        ExposedFindAllUsersQuery(database, outputPort, context)
    }

    bind<FindUserByNameQuery>() with factory { outputPort: FindUserByNameOutputPort ->
        ExposedFindUserByNameQuery(database, outputPort, context)
    }
}