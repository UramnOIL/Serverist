package organisms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Text

@Composable
fun SignIn(signIn: (email: String, password: String) -> Unit, error: Throwable? = null) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
                }
            }
        }

        Button(
            attrs = {
                onClick {
                    signIn(email, password)
                }
                if (!(email.isNotEmpty() && password.isNotEmpty())) disabled()
            }
        ) {
            Text("Sign In")
        }
    }
}
