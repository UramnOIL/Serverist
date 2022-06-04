package pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.uramnoil.serverist.presentation.SearchServersController
import com.uramnoil.serverist.presentation.ServersViewModel
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Text

@Composable
fun SearchServersPage(controller: SearchServersController, viewModel: ServersViewModel) {
    val servers by viewModel.serversFlow.collectAsState()

    Div {
        H2 { Text("Search servers") }
        Div {
            Button(
                attrs = {
                    onClick { controller.findAllServers() }
                }
            ) {
                Text("検索")
            }
        }

        Div {
            servers.forEach {
                Div {
                    Text(it.name)
                    Div {
                        Text(it.host ?: "N/A")
                        Text(it.port?.toString() ?: "N/A")
                    }
                }
            }
        }
    }
}
