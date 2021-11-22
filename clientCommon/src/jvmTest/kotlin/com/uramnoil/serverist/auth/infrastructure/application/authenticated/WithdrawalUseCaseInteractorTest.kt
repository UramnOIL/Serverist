package com.uramnoil.serverist.auth.infrastructure.application.authenticated

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.features.cookies.*
import io.ktor.http.*
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class WithdrawalUseCaseInteractorTest : FunSpec({
    test("確認") {
        val client = HttpClient(MockEngine) {
            install(HttpCookies) {
                storage = AcceptAllCookiesStorage()
                default {
                    runBlocking {
                        addCookie("https://serverist.com", Cookie("AUTH", "sessionId"))
                    }
                }
            }

            engine {
                addHandler { request ->
                    request.headers[HttpHeaders.Accept] shouldBe "*/*"

                    if ("${request.url.protocol.name}://${request.url.host}" != "https://serverist.com") {
                        return@addHandler respondError(HttpStatusCode.NotFound)
                    }

                    if (request.method != HttpMethod.Post) {
                        return@addHandler respondError(HttpStatusCode.BadRequest)
                    }

                    when (request.url.fullPath) {
                        "/withdrawal" -> {
                            val rawCookies = request.headers[HttpHeaders.Cookie]!!
                            rawCookies shouldBe "AUTH=sessionId"
                        }
                        else -> {
                            return@addHandler respondError(HttpStatusCode.NotFound)
                        }
                    }

                    respondOk("Ok")
                }
            }
        }

        val coroutineContext = currentCoroutineContext()

        val result = suspendCoroutine<Result<Unit>> {
            WithdrawalUseCaseInteractor(
                client,
                "https://serverist.com",
                { result ->
                    it.resume(result)
                },
                coroutineContext
            ).execute()
        }

        result.getOrThrow()
        result.isSuccess.shouldBeTrue()
    }
})
