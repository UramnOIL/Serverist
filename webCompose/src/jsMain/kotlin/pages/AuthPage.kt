package pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.uramnoil.serverist.presentation.AuthController
import com.uramnoil.serverist.presentation.AuthViewModel
import organisms.Activate
import organisms.SignIn
import organisms.SignUp

@Composable
fun AuthPage(controller: AuthController, viewModel: AuthViewModel) {
    val signUpError by viewModel.signInErrorStateFlow.collectAsState()
    val signInError by viewModel.signInErrorStateFlow.collectAsState()
    val activateError by viewModel.activateErrorStateFlow.collectAsState()

    SignUp(controller::signUp, signUpError)
    Activate(controller::activation, signInError)
    SignIn(controller::signIn, activateError)
}
