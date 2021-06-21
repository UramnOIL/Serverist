package com.uramnoil.serverist.backend

import com.uramnoil.serverist.RedisSessionStorage
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.testing.*
import io.ktor.sessions.*
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import kotlin.test.Test
import kotlin.test.assertTrue

data class Login(val id: String)

fun Application.test() {
    val connectionFactory = LettuceConnectionFactory(RedisStandaloneConfiguration())
    connectionFactory.afterPropertiesSet()

    val storage = RedisSessionStorage(connectionFactory)

    install(Sessions) {
        cookie<Login>("SESSION", storage) {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 1000
        }
    }

    install(Routing) {
        get("/login") {
            call.sessions.set(Login("fuga"))
        }

        get {
            println("hoge")
            call.respond(call.sessions.get<Login>()!!.id)
        }
    }
}

class RedisSessionStorageTest {
    @Test
    fun `RedisSessionStorageのテスト`() = withTestApplication(Application::test) {

        cookiesSession {
            handleRequest(HttpMethod.Get, "/login")

            handleRequest(HttpMethod.Get, "").apply {
                assertTrue { response.content!! == "fuga" }
            }
        }
    }
}