import androidx.compose.runtime.*
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.http.HttpRequest
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.network.http.HttpInterceptor
import com.apollographql.apollo3.network.http.HttpInterceptorChain
import com.uramnoil.serverist.SessionId
import com.uramnoil.serverist.application.server.Server
import com.uramnoil.serverist.infrastructure.auth.application.ActivateUseCaseInteractor
import com.uramnoil.serverist.infrastructure.auth.application.SignInUseCaseInteractor
import com.uramnoil.serverist.infrastructure.auth.application.SignUpUseCaseInteractor
import com.uramnoil.serverist.infrastructure.server.application.CreateServerUseCaseInteractor
import com.uramnoil.serverist.infrastructure.server.application.FindAllServersUseCaseInteractor
import com.uramnoil.serverist.presentation.*
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.renderComposable
import pages.AuthPage
import pages.CreateServerPage
import pages.SearchServersPage
import kotlin.coroutines.CoroutineContext

class AuthenticationInterceptor(private val sessionId: SessionId) : HttpInterceptor {
    override suspend fun intercept(request: HttpRequest, chain: HttpInterceptorChain): HttpResponse {
        val builder = request.newBuilder()
        sessionId.sessionId?.let {
            builder.addHeader("Auth", it)
        }
        return chain.proceed(builder.build())
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
        .httpServerUrl("http://localhost:8080/graphql")
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
        var scene by remember { mutableStateOf(Scene.Auth) }

        when (scene) {
            Scene.Auth -> {
                Auth(coroutineContext, httpClient, sessionId) {
                    scene = it
                }
            }
            Scene.Serverist -> {
                Serverist(coroutineContext, apolloClient)
            }
        }
    }
}

@Composable
fun Auth(coroutineContext: CoroutineContext, httpClient: HttpClient, sessionId: SessionId, setScene: (Scene) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
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
        httpClient,
        sessionId,
    ) {
        it.result.fold(
            {
                setScene(Scene.Serverist)
            }
        ) {
            coroutineScope.launch {
                Napier.e("Error", it)
                mutableSignInErrorStateFlow.emit(it)
            }
        }
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

@Composable
fun Serverist(coroutineContext: CoroutineContext, apolloClient: ApolloClient) {
    val coroutineScope = rememberCoroutineScope()

    val mutableServersStateFlow by remember { mutableStateOf(MutableStateFlow<List<Server>>(listOf())) }
    val serversViewModel = ServersViewModel(mutableServersStateFlow)
    val findAllServersUseCaseInputPort = FindAllServersUseCaseInteractor(coroutineContext, apolloClient) {
        it.result.fold(
            { servers ->
                coroutineScope.launch {
                    mutableServersStateFlow.emit(servers)
                }
            },
            { throwable ->
                Napier.d("error", throwable)
            }
        )
    }

    SearchServersPage(SearchServersController(findAllServersUseCaseInputPort), serversViewModel)

    val createServerUseCaseInputPort = CreateServerUseCaseInteractor(coroutineContext, apolloClient) {
        it.result.fold(
            {
            },
            {
            }
        )
    }
    CreateServerPage(CreateServerController(createServerUseCaseInputPort), CreateServerViewModel())
}
