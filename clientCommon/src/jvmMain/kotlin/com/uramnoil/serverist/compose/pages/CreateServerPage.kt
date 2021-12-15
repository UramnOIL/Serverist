package com.uramnoil.serverist.compose.pages

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.uramnoil.serverist.presentation.CreateServerController

@Composable
fun CreateServerPage() {
    val coroutineScope = rememberCoroutineScope()
    val controller by remember {
        val controller = CreateServerController(coroutineScope.coroutineContext) {

        }
        mutableStateOf(controller)
    }


    var name by remember { mutableStateOf("") }
    var host by remember { mutableStateOf("") }
    var port by remember { mutableStateOf<UShort?>(null) }
    var description by remember {
        mutableStateOf("")
    }

    Column {
        Text("Name")
        OutlinedTextField(name, onValueChange = { name = it })

        Text("Host")
        OutlinedTextField(value = host, onValueChange = { host = it })

        Text("Port")
        OutlinedTextField(value = port?.toString() ?: "", onValueChange = {
            port = it.toUShortOrNull() ?: port
        })

        Text("Description")
        OutlinedTextField(value = description, onValueChange = { description = it })

        Button(
            onClick = {
                controller.createServer(name, host.ifEmpty { null }, port, description)
            }
        ) {
            Text("Create")
        }
    }
}


@Preview
@Composable
private fun Preview() {
    CreateServerPage()
}