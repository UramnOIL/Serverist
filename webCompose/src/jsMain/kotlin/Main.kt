import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.http.HttpRequest
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.network.http.HttpInterceptor
import com.apollographql.apollo3.network.http.HttpInterceptorChain
import com.uramnoil.serverist.infrastructure.auth.application.SignInUseCaseInteractor
import com.uramnoil.serverist.infrastructure.auth.application.SignUpUseCaseInteractor
import com.uramnoil.serverist.presentation.AuthController
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.jetbrains.compose.web.renderComposable
import pages.AuthPage

data class Context(var sessionId: String? = null)

class AuthenticationInterceptor(private val context: Context) : HttpInterceptor {
    override suspend fun intercept(request: HttpRequest, chain: HttpInterceptorChain): HttpResponse {
        val newRequest = request.newBuilder()
            .addHeader("Auth", context.sessionId ?: "")
            .build()

        return chain.proceed(newRequest)
    }
}

const val host = "https://serverist.uramnoil.com/"

fun main() {
    val context = Context()
    val apolloClient = ApolloClient.Builder()
        .httpServerUrl(host)
        .addHttpInterceptor(AuthenticationInterceptor(context))
        .build()
    val httpClient = HttpClient()
    val coroutineContext = Dispatchers.Default + Job()

    renderComposable(rootElementId = "root") {
        val signUpUseCaseInputPort = SignUpUseCaseInteractor(
            coroutineContext,
            host,
            httpClient,
        ) {

        }

        val signInUseCaseInputPort = SignInUseCaseInteractor(
            coroutineContext,
            host,
            httpClient
        ) {

        }
        AuthPage(AuthController(signUpUseCaseInputPort, signInUseCaseInputPort))
    }
}