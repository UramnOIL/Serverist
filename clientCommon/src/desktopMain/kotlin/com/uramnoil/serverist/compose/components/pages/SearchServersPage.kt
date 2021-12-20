package com.uramnoil.serverist.compose.components.pages

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.benasher44.uuid.uuidFrom
import com.uramnoil.serverist.presentation.FindServersController
import com.uramnoil.serverist.presentation.ServersViewModel
import com.uramnoil.serverist.serverist.application.server.Server
import kotlinx.datetime.Clock

@Composable
fun SearchServersPage(controller: FindServersController, viewModel: ServersViewModel) {
    val servers by viewModel.serversFlow.collectAsState()

    Column {
        Box(Modifier.fillMaxWidth()) {
            Button(
                {
                    controller.findAllServers()
                },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Text("検索")
            }
        }

        servers.forEach {
            Column {
                Text(it.name)
                Row {
                    Text(it.host ?: "N/A", Modifier.weight(1f))
                    Text(it.port?.toString() ?: "N/A", Modifier.weight(1f))
                }
            }
        }
    }
}


@Preview
@Composable
private fun Preview() {
    val viewModel by remember {
        val servers = (1..100).map {
            Server(
                uuidFrom("50d078aa-405f-8715-7a87-bfe1def0a08d"),
                Clock.System.now(),
                uuidFrom("50d078aa-405f-8715-7a87-bfe1def0a08d"),
                "Server$it",
                "host.com",
                19132,
                "hoge"
            )
        }
        val serversViewModel = ServersViewModel()
        mutableStateOf(serversViewModel)
    }

    val controller by remember {
        mutableStateOf(FindServersController {

        })
    }

    SearchServersPage(controller, viewModel)
}