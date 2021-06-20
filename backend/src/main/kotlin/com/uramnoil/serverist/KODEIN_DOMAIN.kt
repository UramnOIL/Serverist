package com.uramnoil.serverist

import com.uramnoil.serverist.domain.server.repositories.ServerRepository
import com.uramnoil.serverist.domain.user.repositories.UserRepository
import com.uramnoil.serverist.infrastracture.HashPasswordServiceImpl
import com.uramnoil.serverist.infrastracture.server.ExposedServerRepository
import com.uramnoil.serverist.infrastracture.user.ExposedUserRepository
import io.ktor.application.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.factory

fun ApplicationEnvironment.buildDomainDi(di: DI) = DI {
    this.extend(di)
    // <-- User -->

    bind<UserRepository>() with factory {
        ExposedUserRepository()
    }

    // <-- UnapprovedUser -->

    // <-- Server -->

    bind<ServerRepository>() with factory {
        ExposedServerRepository()
    }

    // <-- Password -->

    bind<com.uramnoil.serverist.domain.kernel.services.HashPasswordService>() with factory {
        HashPasswordServiceImpl()
    }
}