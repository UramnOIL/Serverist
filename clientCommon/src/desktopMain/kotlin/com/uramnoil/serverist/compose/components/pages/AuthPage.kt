package com.uramnoil.serverist.compose.components.pages

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import com.uramnoil.serverist.presentation.AuthController

enum class Component {
    SignIn,
    SignUp
}

@Composable
fun AuthPage(controller: AuthController) {
    var component by remember { mutableStateOf(Component.SignUp) }

    Column {
        when (component) {
            Component.SignUp -> {
                SignUp(controller::signUp)
                Button(onClick = { component = Component.SignIn }) {
                    Text("Switch to Sign In")
                }
            }
            Component.SignIn -> {
                SignIn(controller::signIn)
                Button(onClick = { component = Component.SignUp }) {
                    Text("Switch to Sign Up")
                }
            }
        }
    }
}

@Composable
fun SignUp(signUp: (email: String, password: String) -> Unit) {
    var hasSignedIn by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf<Throwable?>(null) }

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
                //inputPort.execute(SignInUseCaseInput(email, password))
            },
            enabled = email.isNotEmpty() && password.isNotEmpty()
        ) {
            Text("Sign In")
        }
    }
}

@Composable
fun SignIn(signIn: (email: String, password: String) -> Unit) {
    var hasSignedUp by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf<Throwable?>(null) }

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
                signIn(email, password)
            },
            enabled = password.isNotEmpty() && isCorrectFormattedPassword
        ) {
            Text("Sign Up")
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AuthPage(AuthController())
}