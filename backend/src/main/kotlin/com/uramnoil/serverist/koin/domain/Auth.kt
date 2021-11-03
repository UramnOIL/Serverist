package com.uramnoil.serverist.koin.domain

import com.uramnoil.serverist.auth.infrastructure.HashPasswordServiceImpl
import com.uramnoil.serverist.domain.auth.kernel.services.HashPasswordService
import io.ktor.application.*
import org.koin.dsl.module

fun Application.buildAuthDomainDI() = module {
    single<HashPasswordService> {
        HashPasswordServiceImpl()
    }
}