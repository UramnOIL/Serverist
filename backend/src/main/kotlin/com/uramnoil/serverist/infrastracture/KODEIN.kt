package com.uramnoil.serverist.infrastracture

import com.uramnoil.serverist.application.usecases.server.commands.CreateServerCommand
import com.uramnoil.serverist.application.usecases.server.commands.DeleteServerCommand
import com.uramnoil.serverist.application.usecases.server.commands.UpdateServerCommand
import com.uramnoil.serverist.application.usecases.server.queries.FindServerByIdOutputPort
import com.uramnoil.serverist.application.usecases.server.queries.FindServerByIdOutputPortDto
import com.uramnoil.serverist.application.usecases.server.queries.FindServerByIdQuery
import com.uramnoil.serverist.application.usecases.user.commands.CreateUserCommand
import com.uramnoil.serverist.application.usecases.user.commands.DeleteUserCommand
import com.uramnoil.serverist.application.usecases.user.commands.UpdateUserCommand
import com.uramnoil.serverist.domain.service.repositories.NotFoundException
import com.uramnoil.serverist.domain.service.repositories.ServerRepository
import com.uramnoil.serverist.domain.service.repositories.UserRepository
import com.uramnoil.serverist.domain.service.services.server.CreateServerService
import com.uramnoil.serverist.domain.service.services.user.CreateUserService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.kodein.di.*
import kotlin.coroutines.CoroutineContext
import com.uramnoil.serverist.domain.service.models.server.Id as ServerId
import com.uramnoil.serverist.domain.service.models.user.Description as UserDescription
import com.uramnoil.serverist.domain.service.models.user.Id as UserId
import com.uramnoil.serverist.domain.service.models.user.Name as UserName

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

    bind<CreateServerCommand>() with singleton {
        ExposedCreateServerCommand(database, instance(), instance(), context)
    }

    bind<DeleteServerCommand>() with singleton {
        ExposedDeleteServerCommand(database, instance(), context)
    }

    bind<UpdateServerCommand>() with singleton {
        ExposedUpdateServerCommand(database, instance(), context)
    }

    bind<FindServerByIdQuery>() with factory { outputPort: FindServerByIdOutputPort ->
        FindServerByIdQuery {
            val serverRepository: ServerRepository = instance()

            scope.launch {
                outputPort.handle(it.let {
                    serverRepository.findByIdAsync(ServerId(it.id)).await()?.run {
                        FindServerByIdOutputPortDto(
                            id.value,
                            owner.value,
                            name.value,
                            address.value,
                            port.value,
                            description.value
                        )
                    }
                })
            }
        }
    }

    // <--- User --->

    bind<CreateUserCommand>() with singleton {
        CreateUserCommand {
            val createUserService: CreateUserService = instance()

            scope.launch {
                createUserService.newAsync(it.name, it.description).await()
            }
        }
    }

    bind<DeleteUserCommand>() with singleton {
        DeleteUserCommand {
            val userRepository: UserRepository = instance()

            scope.launch {
                userRepository.findByIdAsync(UserId(it.id)).await()?.let {
                    userRepository.deleteAsync(it)
                } ?: throw NotFoundException("DeleteUserCommand#execute: ユーザー(ID: ${it.id})が見つかりませんでした。")
            }
        }
    }

    bind<UpdateUserCommand>() with singleton {
        UpdateUserCommand {
            val repository: UserRepository = instance()

            scope.launch {
                newSuspendedTransaction(db = database) {
                    repository.findByIdAsync(UserId(it.id)).await()?.apply {
                        name = UserName(it.name)
                        description = UserDescription(it.description)

                        repository.storeAsync(this)
                    } ?: throw NotFoundException("UpdateUserCommand#execute: ユーザー(ID: ${it.id})が見つかりませんでした。")
                }
            }
        }
    }
}