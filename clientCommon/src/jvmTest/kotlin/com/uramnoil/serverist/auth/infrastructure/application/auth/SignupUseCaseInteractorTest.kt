package com.uramnoil.serverist.auth.infrastructure.application.auth

import com.uramnoil.serverist.infrastructure.application.auth.SignupUseCaseInteractor
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SignupUseCaseInteractorTest : FunSpec({

    test("確認") {
        val email = "uramnoil@example.com"
        val password = "hoge1234"

        val client = HttpClient(MockEngine) {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
                acceptContentTypes = acceptContentTypes + ContentType.Application.Json
            }
            engine {
                addHandler { request ->
                    if ("${request.url.protocol.name}://${request.url.host}${request.url.fullPath}" != "https://serverist.com/signup") {
                        return@addHandler respondError(HttpStatusCode.NotFound)
                    }
                    if (request.method != HttpMethod.Post) {
                        return@addHandler respondError(HttpStatusCode.BadRequest)
                    }
                    if (request.body.contentType != ContentType.Application.Json) {
                        return@addHandler respondError(HttpStatusCode.BadRequest)
                    }

                    @Serializable
                    data class Credential(val email: String, val password: String)


                    val content = request.body.toByteArray().decodeToString()
                    val (e, p) = Json.decodeFromString<Credential>(content)

                    e shouldBe email
                    p shouldBe password

                    respondOk("Ok")
                }
            }
        }

        val coroutineContext = currentCoroutineContext()

        val result = suspendCoroutine<Result<Unit>> {
            SignupUseCaseInteractor(
                "https://serverist.com",
                client,
                { result ->
                    it.resume(result)
                },
                coroutineContext
            ).execute(email, password)
        }

        result.isSuccess.shouldBeTrue()
    }
})
