package com.uramnoil.serverist.compose.components.pages

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.benasher44.uuid.uuidFrom
import com.uramnoil.serverist.application.server.FindAllServersQueryUseCaseInput
import com.uramnoil.serverist.application.server.FindAllServersQueryUseCaseInputPort
import com.uramnoil.serverist.application.server.Server
import kotlinx.datetime.Clock

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
        object : FindAllServersQueryUseCaseInputPort {
            override fun execute(input: FindAllServersQueryUseCaseInput) {
            }
        }
    }
}



