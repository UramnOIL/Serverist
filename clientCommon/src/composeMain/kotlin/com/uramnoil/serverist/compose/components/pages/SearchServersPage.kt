package com.uramnoil.serverist.compose.components.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.uramnoil.serverist.application.OrderBy
import com.uramnoil.serverist.application.Sort
import com.uramnoil.serverist.application.server.FindAllServersQueryUseCaseInput
import com.uramnoil.serverist.application.server.FindAllServersQueryUseCaseInputPort
import com.uramnoil.serverist.application.server.FindAllServersQueryUseCaseOutputPort
import com.uramnoil.serverist.presentation.FindServersPresenter
import com.uramnoil.serverist.presentation.ServersViewModel
import kotlin.coroutines.CoroutineContext

internal val serversViewModel = ServersViewModel()

@Composable
fun SearchServersPage(inputBuilder: (CoroutineContext, outputPort: FindAllServersQueryUseCaseOutputPort) -> FindAllServersQueryUseCaseInputPort) {
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
                    inputPort.execute(FindAllServersQueryUseCaseInput(100, 0, Sort.Desc, OrderBy.CreatedAt))
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

