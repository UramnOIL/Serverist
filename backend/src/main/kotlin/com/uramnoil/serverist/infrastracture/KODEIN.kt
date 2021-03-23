package com.uramnoil.serverist.infrastracture

import com.uramnoil.serverist.application.usecases.server.commands.CreateServerCommand
import com.uramnoil.serverist.application.usecases.server.commands.DeleteServerCommand
import com.uramnoil.serverist.application.usecases.server.commands.UpdateServerCommand
import com.uramnoil.serverist.application.usecases.server.queries.FindServerByIdOutputPort
import com.uramnoil.serverist.application.usecases.server.queries.FindServerByIdQuery
import com.uramnoil.serverist.application.usecases.user.commands.CreateUserCommand
import com.uramnoil.serverist.application.usecases.user.commands.DeleteUserCommand
import com.uramnoil.serverist.application.usecases.user.commands.UpdateUserCommand
import com.uramnoil.serverist.domain.service.models.user.Id
import com.uramnoil.serverist.domain.service.repositories.ServerRepository
import com.uramnoil.serverist.domain.service.repositories.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.*
import kotlin.coroutines.CoroutineContext

fun buildDi(context: CoroutineContext) = DI {
    bind<ServerRepository>() with singleton {
        TODO()
    }

    bind<UserRepository>() with singleton {
        TODO()
    }

    bind<CreateServerCommand>() with singleton {
        CreateServerCommand {
            val userRepository: UserRepository = instance()
            val serverRepository: ServerRepository = instance()

            GlobalScope.launch(context) {
                val user = userRepository.findById(Id(it.ownerId))
                serverRepository.store()
            }
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