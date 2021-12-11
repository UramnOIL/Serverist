package com.uramnoil.serverist.infrastructure.user.application

import com.uramnoil.serverist.application.user.FindAllUsersQueryUseCaseInput
import com.uramnoil.serverist.application.user.FindAllUsersQueryUseCaseInputPort
import com.uramnoil.serverist.application.user.FindAllUsersQueryUseCaseOutputPort
import io.ktor.client.*
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

class FindAllUsersQueryUseCaseInputPortInteractor(
    private val httpClient: HttpClient,
    private val url: String,
    private val outputPort: FindAllUsersQueryUseCaseOutputPort,
    coroutineContext: CoroutineContext
) : FindAllUsersQueryUseCaseInputPort, CoroutineScope by CoroutineScope(coroutineContext) {
    override fun execute(input: FindAllUsersQueryUseCaseInput) {
        TODO("Not yet implemented")
    }
}