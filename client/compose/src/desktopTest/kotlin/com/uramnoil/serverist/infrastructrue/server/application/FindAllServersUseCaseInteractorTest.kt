package com.uramnoil.serverist.infrastructrue.server.application

import com.apollographql.apollo3.ApolloClient
import com.uramnoil.serverist.application.OrderBy
import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.application.server.FindAllServersUseCaseInput
import com.uramnoil.serverist.infrastructure.server.application.FindAllServersUseCaseInteractor
import io.kotest.core.spec.style.FunSpec
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FindAllServersUseCaseInteractorTest : FunSpec({
    test("確認") {
        val apolloClient = ApolloClient.Builder()
            .httpServerUrl("http://localhost:8080/graphql")
            .build()
        suspendCoroutine<Unit> {
            FindAllServersUseCaseInteractor(Dispatchers.Default, apolloClient) { output ->
                val servers = output.result.getOrThrow()
                println(servers.count())
                it.resume(Unit)
            }.execute(input = FindAllServersUseCaseInput(100, 0, Sort.Desc, OrderBy.CreatedAt))
        }
    }
})
