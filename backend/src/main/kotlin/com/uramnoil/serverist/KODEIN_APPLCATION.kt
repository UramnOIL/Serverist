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
import com.uramnoil.serverist.infrastracture.server.CreateServerCommandImpl
import com.uramnoil.serverist.infrastracture.server.DeleteServerCommandImpl
import com.uramnoil.serverist.infrastracture.server.FindServerByIdQueryImpl
import com.uramnoil.serverist.infrastracture.server.UpdateServerCommmandImpl
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
        CreateServerCommandImpl(instance(), instance(), context)
    }

    bind<DeleteServerCommand>() with factory {
        DeleteServerCommandImpl(instance(), context)
    }

    bind<UpdateServerCommand>() with factory {
        UpdateServerCommmandImpl(database, instance(), context)
    }

    bind<FindServerByIdQuery>() with factory { outputPort: FindServerByIdOutputPort ->
        FindServerByIdQueryImpl(instance(), outputPort, context)
    }

    // <-- User -->

    bind<CreateUserCommand>() with factory {
        CreateUserCommandImpl(instance(), context)
    }

    bind<DeleteUserCommand>() with factory {
        DeleteUserCommandImpl(instance(), context)
    }

    bind<UpdateUserCommand>() with factory {
        UpdateUserCommandImpl(instance(), context)
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
