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
import com.uramnoil.serverist.domain.service.models.server.Address
import com.uramnoil.serverist.domain.service.models.server.Description
import com.uramnoil.serverist.domain.service.models.server.Name
import com.uramnoil.serverist.domain.service.models.server.Port
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
import com.uramnoil.serverist.domain.service.models.user.Id as UserId

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
        CreateServerCommand {
            val userRepository: UserRepository = instance()
            val createServerService: CreateServerService = instance()

            scope.launch {
                newSuspendedTransaction(db = database) {
                    val user = userRepository.findByIdAsync(UserId(it.ownerId)).await()
                        ?: throw NotFoundException("CreateServerCommand#execute: ユーザー(Id: ${it.ownerId})が見つかりませんでした。")
                    createServerService.newAsync(it.name, user, it.address, it.port, it.description).await()
                }
            }
        }
    }

    bind<DeleteServerCommand>() with singleton {
        DeleteServerCommand {
            val serverRepository: ServerRepository = instance()

            scope.launch {
                newSuspendedTransaction {
                    val server = serverRepository.findByIdAsync(ServerId(it.id)).await()
                        ?: throw NotFoundException("DeleteServerCommand#Execute: サーバー(Id: ${it.id})が見つかりませんでした。")
                    serverRepository.deleteAsync(server)
                }
            }
        }
    }

    bind<UpdateServerCommand>() with singleton {
        UpdateServerCommand {
            val serverRepository: ServerRepository = instance()

            scope.launch {
                newSuspendedTransaction {
                    val server = serverRepository.findByIdAsync(ServerId(it.id)).await()
                        ?: throw NotFoundException("UpdateServerCommand#excecute: サーバー(Id: ${it.id})が見つかりませんでした。")
                    server.apply {
                        name = Name(it.name)
                        address = Address(it.address)
                        port = Port(it.port)
                        description = Description(it.description)
                    }
                    serverRepository.storeAsync(server)
                }
            }
        }
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
                createUserService.newAsync(it.name, it.description)
            }
        }
    }

    bind<DeleteUserCommand>() with singleton {
        DeleteUserCommand {
            val userRepository: UserRepository = instance()

            scope.launch {
                userRepository.findByIdAsync(UserId(it.id)).await()?.let {
                    userRepository.deleteAsync(it)
                }
            }
        }
    }

    bind<UpdateUserCommand>() with singleton {
        UpdateUserCommand {
            TODO()
        }
    }
}