package com.uramnoil.serverist.presentation

import com.uramnoil.serverist.serverist.application.server.Server
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class EditServerViewModel(internal val mutableServerFlow: MutableSharedFlow<Server>) {
    val serverStateFlow: Flow<Server>
        get() = mutableServerFlow
}