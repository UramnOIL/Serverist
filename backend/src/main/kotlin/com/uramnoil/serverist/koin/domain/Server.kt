package com.uramnoil.serverist.koin.domain

import com.uramnoil.serverist.domain.server.repositories.ServerRepository
import com.uramnoil.serverist.server.infrastructure.comain.repositories.ExposedServerRepository
import io.ktor.application.*
import org.koin.dsl.module

fun Application.buildServerDomainDI() = module {
    single<ServerRepository> {
        ExposedServerRepository()
    }
}