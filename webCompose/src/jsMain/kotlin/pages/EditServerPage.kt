package pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.presentation.EditServerController
import com.uramnoil.serverist.presentation.EditServerViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Text

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
        H1 { Text("Edit Server") }
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