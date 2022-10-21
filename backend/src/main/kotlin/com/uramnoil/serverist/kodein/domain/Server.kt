package com.uramnoil.serverist.kodein.domain

import com.uramnoil.serverist.domain.server.repositories.ServerRepository
import com.uramnoil.serverist.infrastracture.server.domain.repositories.ExposedServerRepository
import io.ktor.application.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.factory

fun Application.buildServerDomainDI() = DI {
    bind<ServerRepository>() with factory {
        ExposedServerRepository()
    }
}