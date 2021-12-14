package com.uramnoil.serverist.compose.components.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType
import com.uramnoil.serverist.application.auth.SignInUseCaseInput
import com.uramnoil.serverist.application.auth.SignInUseCaseInputPort
import com.uramnoil.serverist.application.auth.SignInUseCaseOutputPort
import kotlin.coroutines.CoroutineContext

typealias SignInUseCaseInputPortFactory = (CoroutineContext, SignInUseCaseOutputPort) -> SignInUseCaseInputPort

@Composable
fun SignInPage(factory: SignInUseCaseInputPortFactory, onSignedIn: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val coroutineContext = coroutineScope.coroutineContext

    var hasSignedIn by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf<Throwable?>(null) }

    val inputPort = remember {
        val outputPort = SignInUseCaseOutputPort { output ->
            val (result) = output
            result.fold(
                {
                    onSignedIn
                },
                { throwable ->
                    loginError = throwable
                }
            )
        }
        factory(coroutineContext, outputPort)
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column {
        Text("E-Mail")
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            singleLine = true
        )

        Text("Password")
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true
        )

        Button(
            onClick = {
                inputPort.execute(SignInUseCaseInput(email, password))
            },
            enabled = email.isNotEmpty() && password.isNotEmpty()
        ) {
            Text("Sign In")
        }
    }
}