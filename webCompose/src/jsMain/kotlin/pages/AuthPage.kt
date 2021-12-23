package pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.uramnoil.serverist.presentation.AuthController
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Text

enum class Component {
    SignIn,
    SignUp
}

@Composable
fun AuthPage(controller: AuthController) {
    var component by remember { mutableStateOf(Component.SignUp) }

    Div {
        when (component) {
            Component.SignUp -> {
                SignUp(controller::signUp)
                Button(
                    attrs = {
                        onClick { component = Component.SignIn }
                    }
                ) {
                    Text("Switch to Sign In")
                }
            }
            Component.SignIn -> {
                SignIn(controller::signIn)
                Button(
                    attrs = {
                        onClick { component = Component.SignUp }
                    }
                ) {
                    Text("Switch to Sign Up")
                }
            }
        }
    }
}

@Composable
fun SignUp(signUp: (email: String, password: String) -> Unit) {
    var hasSignedIn by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf<Throwable?>(null) }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Div {
        H1 { Text("Sign Up") }

        Div {
            Text("E-Mail")
            Input(InputType.Email) {
                onInput {
                    it.value
                }
            }
        }

        Div {
            Text("Password")
            Input(InputType.Password) {
                onInput {
                    password = it.value
                }
            }
        }

        Button(
            attrs = {
                onClick {
                    signUp(email, password)
                }
                if (email.isNotEmpty() && password.isNotEmpty()) disabled()
            },
        ) {
            Text("Sign In")
        }
    }
}

@Composable
fun SignIn(signIn: (email: String, password: String) -> Unit) {
    var hasSignedUp by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf<Throwable?>(null) }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isCorrectFormattedPassword by remember { mutableStateOf(false) }

    Div {
        H1 { Text("Sign In") }

        Div {
            Text("E-Mail")
            Input(InputType.Email) {
                onInput { email = it.value }
            }
        }

        Div {

            Text("Password")
            Input(InputType.Password) {
                onInput {
                    password = it.value
                    val regex =
                        Regex("""^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9!"#$%&'()*+,\-./:;<=>?\[\\\]^_`{|}~]{8,}$""")
                    isCorrectFormattedPassword = regex.matches(password)
                }
            }
            if (!isCorrectFormattedPassword) Text("Incorrect password")
        }

        Button(
            attrs = {
                onClick {
                    signIn(email, password)
                }
                if (password.isNotEmpty() && isCorrectFormattedPassword) disabled()
            }
        ) {
            Text("Sign Up")
        }
    }
}