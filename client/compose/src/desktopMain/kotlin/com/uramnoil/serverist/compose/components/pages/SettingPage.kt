package com.uramnoil.serverist.compose.components.pages

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.uramnoil.serverist.presentation.SettingController
import com.uramnoil.serverist.presentation.SettingViewModel

@Composable
fun SettingPage(controller: SettingController, viewModel: SettingViewModel) {
    var accountId by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column {
        Text("Account ID")
        OutlinedTextField(value = accountId, onValueChange = { accountId = it })
        Text("Name")
        OutlinedTextField(value = name, onValueChange = { name = it })
        Text("Description")
        OutlinedTextField(value = description, onValueChange = { description = it })

        Box {
            Button(onClick = {
                controller.updateUser(accountId, name, description)
            }, modifier = Modifier.align(Alignment.CenterEnd)) {
                Text("Apply")
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    val viewModel by remember {
        mutableStateOf(SettingViewModel())
    }

    val controller by remember {
        mutableStateOf(
            SettingController {
            }
        )
    }

    SettingPage(controller, viewModel)
}
