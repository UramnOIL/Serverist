package com.uramnoil.serverist.compose.components.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.benasher44.uuid.Uuid
import com.uramnoil.serverist.presentation.EditServerController
import com.uramnoil.serverist.presentation.EditServerViewModel
import kotlinx.coroutines.flow.collect

@Composable
fun EditServerPage(id: Uuid, controller: EditServerController, viewModel: EditServerViewModel) {
    var name by remember { mutableStateOf("") }
    var host by remember { mutableStateOf("") }
    var port by remember { mutableStateOf<UShort?>(null) }
    var description by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.serverStateFlow.collect {
            name = it.name
            host = it.host ?: ""
            port = it.port?.toUShort()
            description = it.description
        }
        controller.findServer(id)
    }

    Column {
        Text("Name")
        OutlinedTextField(value = name, onValueChange = { name = it })
        Text("Host")
        OutlinedTextField(value = host, onValueChange = { host = it })
        Text("Port")
        OutlinedTextField(
            value = port?.toString() ?: "",
            onValueChange = {
                port = it.toUShortOrNull() ?: port
            }
        )
        Text("Description")
        OutlinedTextField(value = description, onValueChange = { description = it })
        Button(
            onClick = {
                controller.updateServer(id, name, host, port, description)
            }
        ) {
            Text("Apply")
        }


        Button(
            onClick = {
                controller.deleteServer(id)
            }
        ) {
            Text("Delete")
        }
    }
}