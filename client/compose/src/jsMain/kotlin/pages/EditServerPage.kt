package pages

import androidx.compose.runtime.*
import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.presentation.EditServerController
import com.uramnoil.serverist.presentation.EditServerViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.dom.*

@Composable
fun EditServerPage(id: Uuid, controller: EditServerController, viewModel: EditServerViewModel) {
    var name by remember { mutableStateOf("") }
    var host by remember { mutableStateOf("") }
    var port by remember { mutableStateOf<UShort?>(null) }
    var description by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        launch {
            viewModel.serverStateFlow.collect {
                name = it.name
                host = it.host ?: ""
                port = it.port?.toUShort()
                description = it.description
            }
        }
        launch {
            controller.findServer(id)
        }
    }

    Div {
        H2 { Text("Edit Server") }
        Div {
            Text("Name")
            Input(InputType.Text) {
                value(name)
                onChange { name = it.value }
            }
        }
        Div {
            Text("Host")
            Input(InputType.Text) {
                value(host)
                onChange { host = it.value }
            }
        }
        Div {
            Text("Port")
            Input(InputType.Text) {
                value(port?.toString() ?: "")
                onChange {
                    port = it.value.toUShortOrNull() ?: port
                }
            }
        }
        Div {
            Text("Description")
            Input(InputType.Text) {
                value(description)
                onChange { description = it.value }
            }
        }
        Button(
            attrs = {
                onClick {
                    controller.updateServer(id, name, host, port, description)
                }
            },
        ) {
            Text("Apply")
        }

        Button(
            attrs = {
                onClick {
                    controller.deleteServer(id)
                }
            }
        ) {
            Text("Delete")
        }
    }
}
