package pages

import androidx.compose.runtime.*
import com.uramnoil.serverist.presentation.SettingController
import com.uramnoil.serverist.presentation.SettingViewModel
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.dom.*

@Composable
fun SettingPage(controller: SettingController, viewModel: SettingViewModel) {
    var accountId by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Div {
        H1 { Text("Setting") }
        Div {
            Text("Account ID")
            Input(InputType.Text) {
                value(accountId)
                onChange { accountId = it.value }
            }
        }
        Div {
            Text("Name")
            Input(InputType.Text) {
                value(name)
                onChange { name = it.value }
            }
        }
        Div {
            Text("Description")
            Input(InputType.Text) {
                value(description)
                onChange { description = it.value }
            }
        }

        Div {
            Button(
                attrs = {
                    onClick {
                        controller.updateUser(accountId, name, description)
                    }
                }
            ) {
                Text("Apply")
            }
        }
    }
}