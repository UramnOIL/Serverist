package com.uramnoil.serverist.compose.pages

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
import com.uramnoil.serverist.application.OrderBy
import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.application.server.FindAllServersUseCaseInput
import com.uramnoil.serverist.application.server.FindAllServersUseCaseInputPort
import com.uramnoil.serverist.application.server.FindAllServersUseCaseOutputPort
import com.uramnoil.serverist.presentation.FindServersPresenter
import com.uramnoil.serverist.presentation.ServersViewModel
import com.uramnoil.serverist.serverist.application.server.Server
import kotlinx.datetime.Clock
import kotlin.coroutines.CoroutineContext

internal val serversViewModel = ServersViewModel()

@Composable
fun SearchServersPage(inputBuilder: (CoroutineContext, outputPort: FindAllServersUseCaseOutputPort) -> FindAllServersUseCaseInputPort) {
    val coroutineScope = rememberCoroutineScope()
    val coroutineContext = coroutineScope.coroutineContext

    val servers by serversViewModel.serversFlow.collectAsState()
    val inputPort by remember {
        val presenter = FindServersPresenter(serversViewModel, coroutineContext)
        val inputPort = inputBuilder(coroutineContext, presenter)
        mutableStateOf(inputPort)
    }



    Column {
        Box(Modifier.fillMaxWidth()) {
            Button(
                {
                    inputPort.execute(FindAllServersUseCaseInput(100, 0, Sort.Desc, OrderBy.CreatedAt))
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
    LaunchedEffect(Unit) {
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
        serversViewModel.serversMutableFlow.emit(servers)
    }

    SearchServersPage { coroutineContext, outputPort ->
        FindAllServersUseCaseInputPort {
        }
    }
}