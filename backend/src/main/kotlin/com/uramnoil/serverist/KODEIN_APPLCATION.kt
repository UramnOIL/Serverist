package com.uramnoil.serverist

import com.uramnoil.serverist.application.server.commands.CreateServerCommand
import com.uramnoil.serverist.application.server.commands.DeleteServerCommand
import com.uramnoil.serverist.application.server.commands.UpdateServerCommand
import com.uramnoil.serverist.application.server.queries.FindServerByIdOutputPort
import com.uramnoil.serverist.application.server.queries.FindServerByIdQuery
import com.uramnoil.serverist.application.user.commands.CreateUserCommand
import com.uramnoil.serverist.application.user.commands.DeleteUserCommand
import com.uramnoil.serverist.application.user.commands.UpdateUserCommand
import com.uramnoil.serverist.application.user.queries.*
import com.uramnoil.serverist.infrastracture.service.ExposedCreateServerCommand
import com.uramnoil.serverist.infrastracture.service.ExposedDeleteServerCommand
import com.uramnoil.serverist.infrastracture.service.ExposedFindServerByIdQuery
import com.uramnoil.serverist.infrastracture.service.ExposedUpdateServerCommand
import com.uramnoil.serverist.infrastracture.user.*
import org.jetbrains.exposed.sql.Database
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.factory
import org.kodein.di.instance
import kotlin.coroutines.CoroutineContext

fun buildApplicationDi(di: DI, database: Database, context: CoroutineContext) = DI {
    this.extend(di)

    // <-- Server -->
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

    // <-- User -->

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

    bind<FindUserByAccountIdQuery>() with factory { outputPort: FindUserByAccountIdQueryOutputPort ->
        ExposedFindUserByAccountIdQuery(database, outputPort, context)
    }

    bind<FindUserByNameQuery>() with factory { outputPort: FindUserByNameOutputPort ->
        ExposedFindUserByNameQuery(database, outputPort, context)
    }

    // <-- UnapprovedUser -->


}
