import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.http.HttpRequest
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.network.http.HttpInterceptor
import com.apollographql.apollo3.network.http.HttpInterceptorChain
import com.uramnoil.serverist.SessionId
import com.uramnoil.serverist.infrastructure.auth.application.ActivateUseCaseInteractor
import com.uramnoil.serverist.infrastructure.auth.application.SignInUseCaseInteractor
import com.uramnoil.serverist.infrastructure.auth.application.SignUpUseCaseInteractor
import com.uramnoil.serverist.infrastructure.server.application.FindAllServersUseCaseInteractor
import com.uramnoil.serverist.presentation.AuthController
import com.uramnoil.serverist.presentation.AuthViewModel
import com.uramnoil.serverist.presentation.SearchServersController
import com.uramnoil.serverist.presentation.ServersViewModel
import com.uramnoil.serverist.serverist.application.server.Server
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.renderComposable
import pages.AuthPage
import pages.SearchServersPage

class AuthenticationInterceptor(private val sessionId: SessionId) : HttpInterceptor {
    override suspend fun intercept(request: HttpRequest, chain: HttpInterceptorChain): HttpResponse {
        val newRequest = request.newBuilder()
            .addHeader("Auth", sessionId.sessionId ?: "")
            .build()

        println(request.body)

        return chain.proceed(newRequest)
    }
}

enum class Scene {
    Auth,
    Serverist,
}

const val host = "http://localhost:8080"

fun main() {
    val sessionId = SessionId()
    val apolloClient = ApolloClient.Builder()
        .httpServerUrl(host)
        .addHttpInterceptor(AuthenticationInterceptor(sessionId))
        .build()
    val httpClient = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                Json {
                    prettyPrint = true
                    isLenient = true
                }
            )
        }
        install(Logging) {
            level = LogLevel.ALL
        }
    }
    val coroutineContext = Dispatchers.Default + Job()
    Napier.base(DebugAntilog())

    renderComposable(rootElementId = "root") {
        val coroutineScope = rememberCoroutineScope()
        var scene by remember { mutableStateOf(Scene.Serverist) }

        when (scene) {
            Scene.Auth -> {
                val mutableSignUpErrorStateFlow = MutableStateFlow<Throwable?>(null)
                val mutableActivateErrorStateFlow = MutableStateFlow<Throwable?>(null)
                val mutableSignInErrorStateFlow = MutableStateFlow<Throwable?>(null)

                val signUpUseCaseInputPort = SignUpUseCaseInteractor(
                    coroutineContext,
                    sessionId,
                    host,
                    httpClient,
                ) {
                    it.result.onFailure {
                        coroutineScope.launch {
                            Napier.e("Error", it)
                            mutableSignUpErrorStateFlow.emit(it)
                        }
                    }
                }

                val activateUseCaseInputPort = ActivateUseCaseInteractor(coroutineContext, httpClient, host) {
                    it.result.onFailure {
                        coroutineScope.launch {
                            Napier.e("Error", it)
                            mutableActivateErrorStateFlow.emit(it)
                        }
                    }
                }

                val signInUseCaseInputPort = SignInUseCaseInteractor(
                    coroutineContext,
                    host,
                    httpClient
                ) {
                    it.result.fold(
                        {
                            scene = Scene.Serverist
                        }, {
                            coroutineScope.launch {
                                Napier.e("Error", it)
                                mutableSignInErrorStateFlow.emit(it)
                            }
                        }
                    )
                }
                AuthPage(
                    AuthController(signUpUseCaseInputPort, activateUseCaseInputPort, signInUseCaseInputPort),
                    AuthViewModel(
                        mutableSignUpErrorStateFlow,
                        mutableSignInErrorStateFlow,
                        mutableActivateErrorStateFlow
                    )
                )
            }
            Scene.Serverist -> {
                val mutableServersStateFlow = MutableStateFlow<List<Server>>(listOf())
                val serversViewModel = ServersViewModel(mutableServersStateFlow)
                val findAllServersUseCaseInputPort = FindAllServersUseCaseInteractor(
                    coroutineContext,
                    apolloClient
                ) {
                    it.result.onSuccess {
                        coroutineScope.launch {
                            mutableServersStateFlow.emit(it)
                        }
                    }
                }
                SearchServersPage(SearchServersController(findAllServersUseCaseInputPort), serversViewModel)
            }
        }
    }
}

