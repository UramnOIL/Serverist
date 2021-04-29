package com.uramnoil.serverist

import com.uramnoil.serverist.domain.repositories.ServerRepository
import com.uramnoil.serverist.domain.repositories.UserRepository
import com.uramnoil.serverist.domain.services.server.CreateServerService
import com.uramnoil.serverist.domain.services.user.CreateUserService
import com.uramnoil.serverist.infrastracture.server.ExposedCreateServerService
import com.uramnoil.serverist.infrastracture.server.ExposedServerRepository
import com.uramnoil.serverist.infrastracture.user.ExposedCreateUserService
import com.uramnoil.serverist.infrastracture.user.ExposedUserRepository
import org.jetbrains.exposed.sql.Database
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton
import kotlin.coroutines.CoroutineContext

fun buildDomainDi(database: Database, context: CoroutineContext) = DI {
    // <-- User -->

    bind<UserRepository>() with singleton {
        ExposedUserRepository(database)
    }

    bind<CreateUserService>() with singleton {
        ExposedCreateUserService(database)
    }

    // <-- UnapprovedUser -->

    // <-- Server -->

    bind<ServerRepository>() with singleton {
        ExposedServerRepository(database)
    }

    bind<CreateServerService>() with singleton {
        ExposedCreateServerService(database)
    }
}