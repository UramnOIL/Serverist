package com.uramnoil.serverist.application.infrastracture

import com.uramnoil.serverist.application.usecases.server.commands.CreateServerCommand
import com.uramnoil.serverist.application.usecases.server.commands.DeleteServerCommand
import com.uramnoil.serverist.application.usecases.server.commands.UpdateServerCommand
import com.uramnoil.serverist.application.usecases.server.queries.FindServerByIdOutputPort
import com.uramnoil.serverist.application.usecases.server.queries.FindServerByIdQuery
import com.uramnoil.serverist.application.usecases.user.commands.CreateUserCommand
import com.uramnoil.serverist.application.usecases.user.commands.DeleteUserCommand
import com.uramnoil.serverist.application.usecases.user.commands.UpdateUserCommand
import com.uramnoil.serverist.domain.service.infrastractures.buildDomain
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.factory
import org.kodein.di.singleton

fun buildApplication() = DI {
    extend(buildDomain())

    // <--- SERVER --->

    bind<CreateServerCommand>() with singleton {
        CreateServerCommand {
            TODO()
        }
    }

    bind<DeleteServerCommand>() with singleton {
        DeleteServerCommand {
            TODO()
        }
    }

    bind<UpdateServerCommand>() with singleton {
        UpdateServerCommand {
            TODO()
        }
    }

    bind<FindServerByIdQuery>() with factory { outputPort: FindServerByIdOutputPort ->
        FindServerByIdQuery {
            TODO()
        }
    }

    // <--- User --->

    bind<CreateUserCommand>() with singleton {
        CreateUserCommand {
            TODO()
        }
    }

    bind<DeleteUserCommand>() with singleton {
        DeleteUserCommand {
            TODO()
        }
    }

    bind<UpdateUserCommand>() with singleton {
        UpdateUserCommand {
            TODO()
        }
    }
}