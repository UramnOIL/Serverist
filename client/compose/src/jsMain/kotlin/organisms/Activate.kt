package organisms

import androidx.compose.runtime.*
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.dom.*

@Composable
fun Activate(activate: (email: String, activationCode: String) -> Unit, error: Throwable? = null) {
    var email by remember { mutableStateOf("") }
    var activationCode by remember { mutableStateOf("") }

    val isCorrectActivationCode = activationCode.length == 6

    Div {
        H2 { Text("Activation") }

        Div {
            Text("E-Mail")
            Input(InputType.Email) {
                onInput { email = it.value }
            }
        }

        Div {

            Text("Activation Code")
            Input(InputType.Text) {
                onInput {
                    if (it.value.length <= 6 && it.value.all { char -> char in '0'..'9' }) {
                        activationCode = it.value
                    }
                }
                value(activationCode)
            }
        }

        Button(
            attrs = {
                onClick {
                    activate(email, activationCode)
                }
                if (!isCorrectActivationCode) disabled()
            }
        ) {
            Text("Activate")
        }
    }
}
