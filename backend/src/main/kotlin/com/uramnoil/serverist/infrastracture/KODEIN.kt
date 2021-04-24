package com.uramnoil.serverist.infrastracture

import com.uramnoil.serverist.application.server.commands.CreateServerCommand
import com.uramnoil.serverist.application.server.commands.DeleteServerCommand
import com.uramnoil.serverist.application.server.commands.UpdateServerCommand
import com.uramnoil.serverist.application.server.queries.FindServerByIdOutputPort
import com.uramnoil.serverist.application.server.queries.FindServerByIdQuery
import com.uramnoil.serverist.application.user.commands.CreateUserCommand
import com.uramnoil.serverist.application.user.commands.DeleteUserCommand
import com.uramnoil.serverist.application.user.commands.UpdateUserCommand
import com.uramnoil.serverist.application.user.queries.FindAllUsersOutputPort
import com.uramnoil.serverist.application.user.queries.FindAllUsersQuery
import com.uramnoil.serverist.application.user.queries.FindUserByNameOutputPort
import com.uramnoil.serverist.application.user.queries.FindUserByNameQuery
import com.uramnoil.serverist.domain.repositories.ServerRepository
import com.uramnoil.serverist.domain.repositories.UserRepository
import com.uramnoil.serverist.domain.services.server.CreateServerService
import com.uramnoil.serverist.domain.services.user.CreateUserService
import com.uramnoil.serverist.infrastracture.service.*
import com.uramnoil.serverist.infrastracture.user.*
import org.jetbrains.exposed.sql.Database
import org.kodein.di.*
import kotlin.coroutines.CoroutineContext

fun buildDi(database: Database, context: CoroutineContext) = DI {
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
        ExposedFindServerByIdQuery(instance(), outputPort, context)
    }

    // <--- User --->

    bind<CreateUserCommand>() with factory {
        ExposedCreateUserCommand(instance(), instance(), context)
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