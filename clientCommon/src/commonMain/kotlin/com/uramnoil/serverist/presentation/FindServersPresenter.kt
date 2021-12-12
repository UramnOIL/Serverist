package com.uramnoil.serverist.presentation

import com.uramnoil.serverist.application.server.FindAllServersQueryUseCaseOutput
import com.uramnoil.serverist.application.server.FindAllServersQueryUseCaseOutputPort
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FindServersPresenter(private val serversViewModel: ServersViewModel, coroutineContext: CoroutineContext) :
    FindAllServersQueryUseCaseOutputPort,
    CoroutineScope by CoroutineScope(coroutineContext) {
    override fun handle(output: FindAllServersQueryUseCaseOutput) {
        launch {
            serversViewModel.run {
                val list = output.result.getOrDefault(serversMutableFlow.value)
                serversViewModel.serversMutableFlow.emit(list)
            }
        }
    }
}