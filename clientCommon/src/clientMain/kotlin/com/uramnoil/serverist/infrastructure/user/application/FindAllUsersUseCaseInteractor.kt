package com.uramnoil.serverist.infrastructure.user.application

import com.uramnoil.serverist.application.user.FindAllUsersUseCaseInput
import com.uramnoil.serverist.application.user.FindAllUsersUseCaseInputPort
import com.uramnoil.serverist.application.user.FindAllUsersUseCaseOutputPort
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
class FindAllUsersUseCaseInteractor(
    private val httpClient: HttpClient,
    private val url: String,
    private val outputPort: FindAllUsersUseCaseOutputPort,
    coroutineContext: CoroutineContext
) : FindAllUsersUseCaseInputPort, CoroutineScope by CoroutineScope(coroutineContext) {
    override fun execute(input: FindAllUsersUseCaseInput) {
        TODO("Not yet implemented")
    }
}