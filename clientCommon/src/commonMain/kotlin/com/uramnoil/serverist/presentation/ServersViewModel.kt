package com.uramnoil.serverist.presentation

import com.uramnoil.serverist.application.server.Server
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ServersViewModel {
    internal val serversMutableFlow = MutableStateFlow(listOf<Server>())
    val serversFlow: StateFlow<List<Server>>
        get() = serversMutableFlow
}