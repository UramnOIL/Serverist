package com.uramnoil.serverist.presentation

import com.uramnoil.serverist.application.server.FindAllServersUseCaseOutput
import com.uramnoil.serverist.application.server.FindAllServersUseCaseOutputPort
import com.uramnoil.serverist.serverist.application.server.Server
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class ServersViewModel {
    internal val serversMutableFlow = MutableStateFlow(listOf<Server>())
    val serversFlow: StateFlow<List<Server>>
        get() = serversMutableFlow
}

class FindServersPresenter(private val serversViewModel: ServersViewModel, coroutineContext: CoroutineContext) :
    FindAllServersUseCaseOutputPort,
    CoroutineScope by CoroutineScope(coroutineContext) {
    override fun handle(output: FindAllServersUseCaseOutput) {
        launch {
            serversViewModel.run {
                val list = output.result.getOrDefault(serversMutableFlow.value)
                serversViewModel.serversMutableFlow.emit(list)
            }
        }
    }
}