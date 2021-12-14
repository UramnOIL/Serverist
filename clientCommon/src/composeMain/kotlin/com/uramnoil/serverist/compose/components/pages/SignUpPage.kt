package com.uramnoil.serverist.compose.components.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.uramnoil.serverist.application.auth.SignUpUseCaseInput
import com.uramnoil.serverist.application.auth.SignUpUseCaseInputPort
import com.uramnoil.serverist.application.auth.SignUpUseCaseOutputPort
import kotlin.coroutines.CoroutineContext

typealias SignUpUseCaseInputPortFactory = (CoroutineContext, SignUpUseCaseOutputPort) -> SignUpUseCaseInputPort

@Composable
fun SignUpPage(factory: SignUpUseCaseInputPortFactory, onSignedUp: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val coroutineContext = coroutineScope.coroutineContext

    var hasSignedUp by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf<Throwable?>(null) }

    val inputPort = remember {
        val outputPort = SignUpUseCaseOutputPort {
            val (result) = it
            result.fold(
                {
                    hasSignedUp = true
                },
                {
                    loginError = it
                }
            )
        }
        factory(coroutineContext, outputPort)
    }

    if (hasSignedUp) onSignedUp()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column {
        Text("E-Mail")
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("hoge@excample.com") },
            singleLine = true
        )

        var isCorrectFormattedPassword by remember { mutableStateOf(false) }

        Text("Password")
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                val regex = Regex("""^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9!"#$%&'()*+,\-./:;<=>?\[\\\]^_`{|}~]{8,}$""")
                isCorrectFormattedPassword = regex.matches(password)
            },
            label = { },
            trailingIcon = {
                if (!isCorrectFormattedPassword) {
                    Icon(Icons.Filled.Warning, "Incorrect format password", tint = Color.Red)
                }
            },
            keyboardOptions = KeyboardOptions.Default,
            singleLine = true
        )

        Button(
            onClick = {
                inputPort.execute(SignUpUseCaseInput(email, password))
            }
        ) {
            Text("Sign Up")
        }
    }
}