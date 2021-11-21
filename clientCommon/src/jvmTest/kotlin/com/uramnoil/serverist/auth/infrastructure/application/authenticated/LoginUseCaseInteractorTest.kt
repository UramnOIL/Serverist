package com.uramnoil.serverist.auth.infrastructure.application.authenticated

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import java.util.*

class LoginUseCaseInteractorTest : FunSpec({
    val email = "uramnoil@example.com"
    val password = "hoge1234"
    val uuid = UUID.randomUUID()

    val client = HttpClient(MockEngine) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
            acceptContentTypes = acceptContentTypes + ContentType.Application.Json
        }
        engine {
            addHandler { request ->
                if ("${request.url.protocol.name}://${request.url.host}${request.url.fullPath}" != "https://serverist.com/login") {
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

                val (e, p) = kotlinx.serialization.json.Json.decodeFromString<Credential>(
                    request.body.toByteArray().decodeToString()
                )
                if (email == e && password == p) {
                    respondOk(kotlinx.serialization.json.Json.encodeToString(mapOf("id" to uuid.toString())))
                } else {
                    respondError(HttpStatusCode.BadRequest)
                }
            }
        }
    }

    test("正しいログイン情報") {
        val useCase = LoginUseCaseInteractor(
            "https://serverist.com",
            client,
            {
                val response = it.getOrNull()
                response shouldBe uuid
            },
            currentCoroutineContext()
        )

        useCase.execute(email, password)
    }
})
