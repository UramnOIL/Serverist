package com.uramnoil.serverist.koin.domain

import com.uramnoil.serverist.domain.auth.kernel.services.HashPasswordService
import com.uramnoil.serverist.infrastructure.HashPasswordServiceImpl
import io.ktor.application.*
import org.koin.dsl.module

fun Application.buildAuthDomainDI() = module {
    single<HashPasswordService> {
        HashPasswordServiceImpl()
    }
}