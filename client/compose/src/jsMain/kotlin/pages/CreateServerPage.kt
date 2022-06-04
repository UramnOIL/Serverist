package pages

import androidx.compose.runtime.*
import com.uramnoil.serverist.presentation.CreateServerController
import com.uramnoil.serverist.presentation.CreateServerViewModel
import kotlinx.browser.window
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.dom.*

@Composable
fun CreateServerPage(controller: CreateServerController, viewModel: CreateServerViewModel) {
    var name by remember { mutableStateOf("") }
    var host by remember { mutableStateOf("") }
    var port by remember { mutableStateOf<UShort?>(null) }
    var description by remember {
        mutableStateOf("")
    }

    Div {
        H2 { Text("Create a new server") }

        Div {
            Text("Name")
            Input(InputType.Text) {
                value(name)
                onInput { name = it.value }
            }
        }

        Div {
            Text("Host")
            Input(InputType.Text) {
                value(host)
                onInput { host = it.value }
            }
        }

        Div {
            Text("Port")
            Input(InputType.Text) {
                value(port?.toString() ?: "")
                onInput {
                    port = if (it.value.isEmpty()) {
                        null
                    } else {
                        it.value.toUShortOrNull() ?: port
                    }
                }
            }
        }

        Div {
            Text("Description")
            Input(InputType.Text) {
                value(description)
                onInput { description = it.value }
            }
        }

        Button(
            attrs = {
                onClick {
                    controller.createServer(name, host.ifEmpty { null }, port, description)
                    window.alert(name)
                }
            },
        ) {
            Text("Create")
        }
    }
}
