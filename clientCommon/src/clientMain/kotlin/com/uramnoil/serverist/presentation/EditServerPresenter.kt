package com.uramnoil.serverist.presentation

import com.uramnoil.serverist.application.server.*
import com.uramnoil.serverist.serverist.application.server.Server
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class EditServerViewModel(internal val mutableServerFlow: MutableSharedFlow<Server>) {
    val serverStateFlow: Flow<Server>
        get() = mutableServerFlow
}

class EditServerPresenter(coroutineContext: CoroutineContext, private val viewModel: EditServerViewModel) :
    UpdateServerUseCaseOutputPort,
    DeleteServerUseCaseOutputPort,
    FindServerByIdUseCaseOutputPort,
    CoroutineScope by CoroutineScope(coroutineContext) {
    override fun handle(output: UpdateServerUseCaseOutput) {
        output.result.getOrElse {
            Napier.e("Could not update the server.", it)
            return
        }
    }

    override fun handle(output: DeleteServerUseCaseOutput) {
        output.result.getOrElse {
            Napier.e("Could not delete the server", it)
        }
    }

    override fun handle(output: FindServerByIdUseCaseOutput) {
        launch {
            val server = output.result.getOrElse {
                Napier.e("Could not get the server info.", it)
                return@launch
            }
            server ?: run {
                Napier.e("Could not find server.")
                return@launch
            }
            viewModel.mutableServerFlow.emit(server)
        }
    }
}