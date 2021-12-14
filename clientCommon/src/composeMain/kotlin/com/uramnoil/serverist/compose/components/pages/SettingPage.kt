package com.uramnoil.serverist.compose.components.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.uramnoil.serverist.application.user.UpdateUserCommandUseCaseInputPort
import com.uramnoil.serverist.application.user.UpdateUserCommandUseCaseOutputPort
import kotlin.coroutines.CoroutineContext


typealias UpdateUserCommandUseCaseInputPortFactory = (CoroutineContext, UpdateUserCommandUseCaseOutputPort) -> UpdateUserCommandUseCaseInputPort

@Composable
fun SettingPage(factory: UpdateUserCommandUseCaseInputPortFactory) {
    val coroutineScope = rememberCoroutineScope()

    var accountId by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val inputPort by remember {
        val inputPort = factory(coroutineScope.coroutineContext) {

        }
        mutableStateOf(inputPort)
    }

    Column {
        Text("Account ID")
        OutlinedTextField(value = accountId, onValueChange = { accountId = it })
        Text("Name")
        OutlinedTextField(value = name, onValueChange = { name = it })
        Text("Description")
        OutlinedTextField(value = description, onValueChange = { description = it })

        Box {
            Button(onClick = { }, modifier = Modifier.align(Alignment.CenterEnd)) {
                Text("Apply")
            }
        }
    }
}